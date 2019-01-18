package com.mozlab.application;

import java.util.Map;
import org.json.JSONException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import com.mozlab.application.dto.CaptainTeamDto;
import com.mozlab.application.dto.response.WrapperResponseDto;
import com.mozlab.application.model.CaptainTeam;

import org.junit.Assert;
import org.junit.Before;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceSelectionCaptainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:test-application.properties")
public class DataController_IntegrationTest {

	@LocalServerPort
	private int port;

	private static boolean triggerDelete = false;

	WrapperResponseDto<CaptainTeamDto> expectedExistCaptain;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();
	// it need for removing created obj in this test
	@Autowired
	MongoRepository<CaptainTeam, Integer> repo;

	private ResponseEntity<Map<String, Boolean>> postRequest(CaptainTeamDto captain, String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String serverUrl = createURLWithPort(url);
		HttpEntity<CaptainTeamDto> body = new HttpEntity<>(captain);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map<String, Boolean>> response = restTemplate.exchange(serverUrl, HttpMethod.POST, body,
				new ParameterizedTypeReference<Map<String, Boolean>>() {
				});
		System.out.println("Response code: " + response.getStatusCode());
		return response;
	}

	private ResponseEntity<WrapperResponseDto<CaptainTeamDto>> isExistCaptain(String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String serverUrl = createURLWithPort(url);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<WrapperResponseDto<CaptainTeamDto>> response = restTemplate.exchange(serverUrl, HttpMethod.GET,
				null, new ParameterizedTypeReference<WrapperResponseDto<CaptainTeamDto>>() {
				});
		System.out.println("Response code: " + response.getStatusCode());
		return response;
	}

	@Before
	public void createExpectedExistCaptain() {

		expectedExistCaptain = new WrapperResponseDto<>();
		expectedExistCaptain.setResult(true);
		expectedExistCaptain.setPayLoad(new CaptainTeamDto(1001, 1111, "Alex"));

	}

	// tests

	// GET

	@Test
	public void testRetrieveIsExistCaptainResponse() throws JSONException {

		ResponseEntity<WrapperResponseDto<CaptainTeamDto>> response = isExistCaptain("/captain/isExist/1001");

		WrapperResponseDto<CaptainTeamDto> result = response.getBody();

		Assert.assertEquals(result.getPayLoad(), expectedExistCaptain.getPayLoad());

		Assert.assertTrue(result.isResult());

		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

	}

	@Test
	public void testRetrieveNoExistCaptainResponse() throws JSONException {

		ResponseEntity<WrapperResponseDto<CaptainTeamDto>> response = isExistCaptain("/captain/isExist/1116666");

		WrapperResponseDto<CaptainTeamDto> result = response.getBody();

		Assert.assertEquals(result.getPayLoad(), null);

		Assert.assertFalse(result.isResult());

		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

	}

	// POST

	// create

	@Test
	public void testRetrieveSuccessCreateCaptainResponse() throws JSONException {

		ResponseEntity<Map<String, Boolean>> response = postRequest(new CaptainTeamDto(99, 1111, "Alex"),
				"/captain/create/");

		Map<String, Boolean> result = response.getBody();

		Assert.assertEquals(result.get("success"), true);

		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

		triggerDelete = true;

	}

	@Test
	public void testRetrieveNoSuccessCreateCaptainResponse() throws JSONException {

		ResponseEntity<Map<String, Boolean>> response = postRequest(new CaptainTeamDto(111, 1111, "Alex"),
				"/captain/create/");

		Map<String, Boolean> result = response.getBody();

		Assert.assertEquals(result.get("success"), false);

		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

	}

	// cancel

	@Test
	public void testRetrieveSuccessCancelCaptainResponse() throws JSONException {

		ResponseEntity<Map<String, Boolean>> response = postRequest(new CaptainTeamDto(1000, 1111, "Alex"),
				"/captain/cancel/");

		Map<String, Boolean> result = response.getBody();

		Assert.assertEquals(result.get("success"), true);

		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

	}

	@Test
	public void testRetrieveNoSuccessCancelCaptainResponse() throws JSONException {

		ResponseEntity<Map<String, Boolean>> response = postRequest(new CaptainTeamDto(1118888, 1111, "Alex"),
				"/captain/cancel/");

		Map<String, Boolean> result = response.getBody();

		Assert.assertEquals(result.get("success"), false);

		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

	}

	// @AfterClass not working with autowired in JUnit4

	@After
	public void deleteCaptainTeam() {

		if (triggerDelete) {
			repo.deleteById(99);

			System.out.println("Delete:CaptainTeam id:" + 99);

			triggerDelete = false;

		}

	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
