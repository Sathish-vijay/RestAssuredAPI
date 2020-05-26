package basicRest;

import org.hamcrest.Matchers;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Demo {

	public static void main(String[] args) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String responseJsonStr = RestAssured.given().log().all()

				.queryParam("key", "qaclick123").body(Payload.addPlacePayload())
				.header("Content-Type", "application/json")

				.when().post("/maps/api/place/add/json")

				.then().log().all()

				.statusCode(200).body("scope", Matchers.equalTo("APP")).body("status", Matchers.equalTo("OK"))
				.header("Server", Matchers.equalTo("Apache/2.4.18 (Ubuntu)")).extract().response().asString();

		System.out.println(responseJsonStr);

		JsonPath js = new JsonPath(responseJsonStr); // String to Json

		String placeId = js.get("place_id");

		System.out.println("Place id is " + placeId);

		System.out.println("Scope is " + js.get("scope"));

	String res  = 	RestAssured.given().log().all()

				.body(Payload.updatePlacePayload(placeId, " new Address")).queryParam("key", "qaclick123")
				.header("Content-Type", "application/json")

				.when().put("/maps/api/place/update/json")

				.then().log().all().statusCode(200).extract().response().asString();
	
	JsonPath json  = new JsonPath(res);
	
	System.out.println(json.get("msg"));

	}

}
