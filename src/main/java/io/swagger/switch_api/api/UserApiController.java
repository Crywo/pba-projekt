package io.swagger.switch_api.api;

import io.swagger.switch_api.model.CreateUserRequest;
import io.swagger.switch_api.model.Error;
import io.swagger.switch_api.model.Task;
import io.swagger.switch_api.model.TaskListResponse;
import io.swagger.switch_api.model.TaskRepository;
import io.swagger.switch_api.model.TaskResponse;
import io.swagger.switch_api.model.User;

import io.swagger.switch_api.model.UserTaskRepository;
import java.util.UUID;
import io.swagger.switch_api.model.UserListResponse;
import io.swagger.switch_api.model.UserRepository;
import io.swagger.switch_api.model.UserResponse;
import io.swagger.switch_api.model.UserTask;
import io.swagger.switch_api.model.UserTaskResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-06-09T10:49:04.429+02:00")

@Controller
public class UserApiController implements UserApi {

    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UserApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }    

    @Autowired
    UserRepository repository;
    
    @Autowired
    UserTaskRepository UserTaskRepository;
    
    @Autowired
    TaskRepository TaskRepository;

    public ResponseEntity<String> createUser(@ApiParam(value = "User object that has to be added" ,required=true )  @Valid @RequestBody CreateUserRequest body,@ApiParam(value = "Podpis X-HMAC-SIGNATURE" ,required=true) @RequestHeader(value="X-HMAC-SIGNATURE", required=true) String X_HMAC_SIGNATURE) {
    	boolean signFlag = false;    	
    	UUID reqId = body.getRequestHeader().getRequestId();
    	
    	TimeZone tz = TimeZone.getTimeZone("GMT+2:00");
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    	df.setTimeZone(tz);
    	String nowAsISO = df.format(new Date());
    	User newUsr = body.getUser();
    	String json = new Gson().toJson(newUsr);
    	String jsonToHash = "{\n"
    			+ "  \"requestHeader\": {\n"
    			+ "    \"requestId\": \"" + body.getRequestHeader().getRequestId() + "\",\n"
    			+ "    \"sendDate\": \"" + body.getRequestHeader().getSendDate() + "\"\n"
    			+ "  },\n"
    			+ "  \"user\": {\n"
    			+ "    \"age\": " + newUsr.getAge() + ",\n"
    			+ "    \"email\": \"" + newUsr.getEmail() + "\",\n"
    			+ "    \"gender\": \"" + newUsr.getGender() + "\",\n"
    			+ "    \"id\": \"" + newUsr.getId() + "\",\n"
    			+ "    \"name\": \"" + newUsr.getName() + "\",\n"
    			+ "    \"password\": \"" + newUsr.getPassword() + "\",\n"
    			+ "    \"roles\": \"" + newUsr.getRoles() + "\",\n"
    			+ "    \"surname\": \"" + newUsr.getSurname() + "\",\n"
    			+ "    \"username\": \"" + newUsr.getUsername() + "\"\n"
    			+ "  }\n"
    			+ "}";

    	//System.out.print("\n"+jsonToHash);
    	try {
    		byte[] secret = "123456".getBytes("UTF-8");
        	byte[] sign = jsonToHash.getBytes("UTF-8");
		    byte[] hashed = calcHmacSha256(secret, sign);
		    String hex = String.format("%032x", new BigInteger(1, hashed));
	    	//System.out.print("\nhex              = " + hex + "\n");
	    	//System.out.print("X_HMAC_SIGNATURE = " + X_HMAC_SIGNATURE + "\n");
		    
	    	if(hex.equals(X_HMAC_SIGNATURE)) {
	    		signFlag = true;
	    	}
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
    	if(signFlag) {
	        String accept = request.getHeader("Accept");
	    	User usr = repository.findOne(newUsr.getId());
	        if (accept != null && accept.contains("application/json")) {
	        	if(usr == null) {
		            repository.save(newUsr);
					return new ResponseEntity<String>("{  \"responseHeader\" : {    \"sendDate\" : \""+nowAsISO+"\",    \"requestId\" : \""+reqId+"\",  \"X-HMAC-SIGNATURE\" : \""+X_HMAC_SIGNATURE+"\" },  \"user\" : "+json+" }", HttpStatus.CREATED);
	        	}
	        	else {
	        		return new ResponseEntity<String>("{ \"code\" : \"422\", \"message\" : \"Entity with the given id already exists\" ,\"responseHeader\" : {    \"sendDate\" : \""+nowAsISO+"\",    \"requestId\" : \""+reqId+"\",  \"X-HMAC-SIGNATURE\" : \""+X_HMAC_SIGNATURE+"\" } }",HttpStatus.UNPROCESSABLE_ENTITY);
	        	}
	        }
    	}
    	else {
    		return new ResponseEntity<String>("{ \"code\" : \"401\", \"message\" : \"Integrity Error, signature is invalid\" ,\"responseHeader\" : {    \"sendDate\" : \""+nowAsISO+"\",    \"requestId\" : \""+reqId+"\",  \"X-HMAC-SIGNATURE\" : \""+X_HMAC_SIGNATURE+"\" } }",HttpStatus.UNAUTHORIZED);    		
    	}

        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<UserListResponse> getAllUsers() {
    	List<User> usrList = repository.findAll();
    	for(User usr : usrList) {
    		usr.setPassword("*******");
    	}
    	String json = new Gson().toJson(usrList);
    	TimeZone tz = TimeZone.getTimeZone("GMT+2:00");
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    	df.setTimeZone(tz);
    	String nowAsISO = df.format(new Date());
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<UserListResponse>(objectMapper.readValue("{  \"usersList\" : "+json+",  \"responseHeader\" : {    \"sendDate\" : \""+nowAsISO+"\",    \"requestId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\"  }}", UserListResponse.class), HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<UserListResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<UserListResponse>(HttpStatus.BAD_REQUEST); 
    }

    public ResponseEntity<String> takeTask(@ApiParam(value = "" ,required=true) @RequestHeader(value="taskId", required=true) UUID taskId,@ApiParam(value = "" ,required=true) @RequestHeader(value="userId", required=true) UUID userId) {
    	TimeZone tz = TimeZone.getTimeZone("GMT+2:00");
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    	df.setTimeZone(tz);
    	String nowAsISO = df.format(new Date());
    	String accept = request.getHeader("Accept");
    	UserTask newUserTask = new UserTask(userId, taskId);
        if (accept != null && accept.contains("application/json")) {
            UserTaskRepository.save(newUserTask);
			String json = new Gson().toJson(newUserTask);
			return new ResponseEntity<String>("{  \"responseHeader\" : {    \"sendDate\" : \""+nowAsISO+"\",    \"requestId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\" },  \"UserTask\" : "+json+" }", HttpStatus.OK);
        }

        return new ResponseEntity<String>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<TaskListResponse> getTaskList(@ApiParam(value = "" ,required=true) @RequestHeader(value="userId", required=true) UUID userId) {
    	List<UserTask> UserTaskList = UserTaskRepository.findAll();
    	List<Task> FilteredList = new ArrayList<Task>();
    	for(UserTask UsrTsk : UserTaskList) {
    		if(UsrTsk.getUserId().equals(userId)) {    			
    			FilteredList.add(TaskRepository.findOne(UsrTsk.getTaskId()));
    		}
    	}
    	String json = new Gson().toJson(FilteredList);
    	TimeZone tz = TimeZone.getTimeZone("GMT+2:00");
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    	df.setTimeZone(tz);
    	String nowAsISO = df.format(new Date());
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<TaskListResponse>(objectMapper.readValue("{  \"tasksList\" : "+json+",  \"responseHeader\" : {    \"sendDate\" : \""+nowAsISO+"\",    \"requestId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\"  }}", TaskListResponse.class), HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<TaskListResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<TaskListResponse>(HttpStatus.BAD_REQUEST); 
    }

    static public byte[] calcHmacSha256(byte[] secretKey, byte[] message) {
        byte[] hmacSha256 = null;
        try {
          Mac mac = Mac.getInstance("HmacSHA256");
          SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "HmacSHA256");
          mac.init(secretKeySpec);
          hmacSha256 = mac.doFinal(message);
        } catch (Exception e) {
          throw new RuntimeException("Failed to calculate hmac-sha256", e);
        }
        return hmacSha256;
      }
    
}
