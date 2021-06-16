package io.swagger.switch_api.api;

import io.swagger.switch_api.model.CreateTaskRequest;
import io.swagger.switch_api.model.Error;
import io.swagger.switch_api.model.Task;
import io.swagger.switch_api.model.TaskListResponse;
import io.swagger.switch_api.model.TaskRepository;
import io.swagger.switch_api.model.TaskResponse;
import java.util.UUID;
import io.swagger.switch_api.model.UpdateTaskRequest;
import io.swagger.switch_api.model.User;
import io.swagger.switch_api.model.UserListResponse;
import io.swagger.switch_api.model.UserRepository;
import io.swagger.switch_api.model.UserResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import io.swagger.Swagger2SpringBoot;
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
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-06-09T10:49:04.429+02:00")

@Controller
public class TaskApiController implements TaskApi {

    private static final Logger log = LoggerFactory.getLogger(TaskApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TaskApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Autowired
    TaskRepository repository;
    
    public ResponseEntity<String> createTask(@ApiParam(value = "Task object that has to be added" ,required=true )  @Valid @RequestBody CreateTaskRequest body,@ApiParam(value = "Podpis X-HMAC-SIGNATURE" ,required=true) @RequestHeader(value="X-HMAC-SIGNATURE", required=true) String X_HMAC_SIGNATURE) {
    	boolean signFlag = false;    	
    	UUID reqId = body.getRequestHeader().getRequestId();
    	
    	TimeZone tz = TimeZone.getTimeZone("GMT+2:00");
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    	df.setTimeZone(tz);
    	String nowAsISO = df.format(new Date());
    	Task newTsk = body.getTask();
    	String json = new Gson().toJson(newTsk);
    	String jsonToHash = "{\n"
    			+ "  \"requestHeader\": {\n"
    			+ "    \"requestId\": \"" + body.getRequestHeader().getRequestId() + "\",\n"
    			+ "    \"sendDate\": \"" + body.getRequestHeader().getSendDate() + "\"\n"
    			+ "  },\n"
    			+ "  \"task\": {\n"
    			+ "    \"description\": \"" + newTsk.getDescription() + "\",\n"
    			+ "    \"id\": \"" + newTsk.getId() + "\",\n"
    			+ "    \"name\": \"" + newTsk.getName() + "\",\n"
    			+ "    \"priority\": " + newTsk.getPriority() + ",\n"
    			+ "    \"status\": \"" + newTsk.getStatus() + "\"\n"
    			+ "  }\n"
    			+ "}";

    	System.out.print("\n"+jsonToHash);
    	try {
    		byte[] secret = "123456".getBytes("UTF-8");
        	byte[] sign = jsonToHash.getBytes("UTF-8");
		    byte[] hashed = calcHmacSha256(secret, sign);
		    String hex = String.format("%032x", new BigInteger(1, hashed));
	    	System.out.print("\nhex              = " + hex + "\n");
	    	System.out.print("X_HMAC_SIGNATURE = " + X_HMAC_SIGNATURE + "\n");
		    
	    	if(hex.equals(X_HMAC_SIGNATURE)) {
	    		signFlag = true;
	    	}
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
    	if(signFlag) {
	        String accept = request.getHeader("Accept");
	    	Task tsk = repository.findOne(newTsk.getId());
	        if (accept != null && accept.contains("application/json")) {
	        	if(tsk == null) {
		            repository.save(newTsk);
					return new ResponseEntity<String>("{  \"responseHeader\" : {    \"sendDate\" : \""+nowAsISO+"\",    \"requestId\" : \""+reqId+"\",  \"X-HMAC-SIGNATURE\" : \""+X_HMAC_SIGNATURE+"\" },  \"task\" : "+json+" }", HttpStatus.CREATED);
	        	}
	        	else {
	        		return new ResponseEntity<String>(HttpStatus.UNPROCESSABLE_ENTITY);
	        	}
	        }
    	}
    	else {
    		return new ResponseEntity<String>("{ \"code\" : \"401\", \"message\" : \"Integrity Error, signature is invalid\" ,\"responseHeader\" : {    \"sendDate\" : \""+nowAsISO+"\",    \"requestId\" : \""+reqId+"\",  \"X-HMAC-SIGNATURE\" : \""+X_HMAC_SIGNATURE+"\" } }",HttpStatus.UNAUTHORIZED);    		
    	}

        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Void> deleteTask(@ApiParam(value = "",required=true) @PathVariable("id") UUID id) {
    	String accept = request.getHeader("Accept");
     	if (accept != null && accept.contains("application/json")) {            
 	    	Task tsk = repository.findOne(id);
 	    	if(tsk != null) {
 	    		repository.delete(id);
 	    		return new ResponseEntity<Void>(HttpStatus.OK);
 	    	}
 	    	else {
 	    		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);    		
 	    	}
     	}
         return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST); 
    }

    public ResponseEntity<TaskListResponse> getAllTasks() {
    	String json = new Gson().toJson(repository.findAll());
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

    public ResponseEntity<TaskResponse> getTaskById(@ApiParam(value = "",required=true) @PathVariable("id") UUID id) {
    	String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
        	Task tsk = repository.findOne(id);
        	TimeZone tz = TimeZone.getTimeZone("GMT+2:00");
        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        	df.setTimeZone(tz);
        	String nowAsISO = df.format(new Date());
        	if(tsk != null) {
        		try {
                	String json = new Gson().toJson(tsk);
					return new ResponseEntity<TaskResponse>(objectMapper.readValue("{  \"responseHeader\" : {    \"sendDate\" : \""+nowAsISO+"\",    \"requestId\" : \"046b6c7f-0b8a-43b9-b35d-6489e6daee91\"  },  \"task\" : "+json+" }", TaskResponse.class), HttpStatus.OK);
				}  
        		catch (IOException e) {
	                log.error("Couldn't serialize response for content type application/json", e);
	                return new ResponseEntity<TaskResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
	            }
        	}
        	else {
        		return new ResponseEntity<TaskResponse>(HttpStatus.NOT_FOUND);    	
        	}
        }

        return new ResponseEntity<TaskResponse>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateTask(@ApiParam(value = "",required=true) @PathVariable("id") UUID id,@ApiParam(value = "" ,required=true )  @Valid @RequestBody UpdateTaskRequest body,@ApiParam(value = "Podpis X-HMAC-SIGNATURE" ,required=true) @RequestHeader(value="X-HMAC-SIGNATURE", required=true) String X_HMAC_SIGNATURE) {
    	boolean signFlag = false;    	
    	UUID reqId = body.getRequestHeader().getRequestId();
    	
    	TimeZone tz = TimeZone.getTimeZone("GMT+2:00");
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    	df.setTimeZone(tz);
    	String nowAsISO = df.format(new Date());
    	Task updatedTsk = body.getTask();
    	String json = new Gson().toJson(updatedTsk);
    	String jsonToHash = "{\n"
    			+ "  \"requestHeader\": {\n"
    			+ "    \"requestId\": \"" + body.getRequestHeader().getRequestId() + "\",\n"
    			+ "    \"sendDate\": \"" + body.getRequestHeader().getSendDate() + "\"\n"
    			+ "  },\n"
    			+ "  \"task\": {\n"
    			+ "    \"description\": \"" + updatedTsk.getDescription() + "\",\n"
    			+ "    \"id\": \"" + updatedTsk.getId() + "\",\n"
    			+ "    \"name\": \"" + updatedTsk.getName() + "\",\n"
    			+ "    \"priority\": " + updatedTsk.getPriority() + ",\n"
    			+ "    \"status\": \"" + updatedTsk.getStatus() + "\"\n"
    			+ "  }\n"
    			+ "}";

    	System.out.print("\n"+jsonToHash);
    	try {
    		byte[] secret = "123456".getBytes("UTF-8");
        	byte[] sign = jsonToHash.getBytes("UTF-8");
		    byte[] hashed = calcHmacSha256(secret, sign);
		    String hex = String.format("%032x", new BigInteger(1, hashed));
	    	System.out.print("\nhex              = " + hex + "\n");
	    	System.out.print("X_HMAC_SIGNATURE = " + X_HMAC_SIGNATURE + "\n");
		    
	    	if(hex.equals(X_HMAC_SIGNATURE)) {
	    		signFlag = true;
	    	}
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
    	if(signFlag) {
	        String accept = request.getHeader("Accept");
	    	Task tsk = repository.findOne(updatedTsk.getId());
	        if (accept != null && accept.contains("application/json")) {
	        	if(tsk != null) {
		            repository.save(updatedTsk);
					return new ResponseEntity<String>("{  \"responseHeader\" : {    \"sendDate\" : \""+nowAsISO+"\",    \"requestId\" : \""+reqId+"\"  },  \"task\" : "+json+" }", HttpStatus.OK);
	        	}
	        	else {
	        		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);    	
	        	}
	        }
    	}
    	else {
    		return new ResponseEntity<String>("{ \"code\" : \"401\", \"message\" : \"Integrity Error, signature is invalid\" ,\"responseHeader\" : {    \"sendDate\" : \""+nowAsISO+"\",    \"requestId\" : \""+reqId+"\",  \"X-HMAC-SIGNATURE\" : \""+X_HMAC_SIGNATURE+"\" } }",HttpStatus.UNAUTHORIZED);    		
    	}

        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
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
