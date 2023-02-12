// 파일 위치 : src/main/java/hello.hellospring.controller/HelloController.java
package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	
//	Web Application에서 /hello가 들어오면 해당 메서드 자동 호출
	@GetMapping("hello")
	public String hello(Model model) {
		
//		src/main/resources:templates의 hello.html 18번째 line 의 ${data}를 hello!!로 변환
//		addAttribute(attributeName, attributeValue) → (키, 값) 구조
		model.addAttribute("data", "hello!!!");
		
//		컨트롤러에서 return 값으로 문자를 반환하면, viewResolver가 화면을 찾아서 처리
//		매핑 장소 `resources:templates/{viewName(return 값)}.html`
		return "hello";
	}
	
	@GetMapping("hello-mvc")
//	외부에서 parameter 받기
	public String helloMvc(@RequestParam(value="name") String name, Model model) {
		model.addAttribute("name", name);
		return "hello-template";
	}
	
	/* 
	 * @ResponseBody 사용 원리			parameter ex `?name=abc`
	 * 1. [웹 브라우저] → [내장 톰켓 서버]로 localhost:8080/hello-api 호출
	 * 2. 스프링 부트 안의 스프링 컨테이너 안의 [HelloController]로 이동
	 * 3. @ResponseBody 존재 有,→ [HttpMessageConverter]로 return:hello(name:abc) 객체 넘겨주기
	 * 4. 단순 문자라면 StringConverter, 객체라면 JsonConverter가 기본으로 동작
	 */
	
//	5. → [웹 브라우저]로 {return 값} 문자 그대로 응답
//	별도의 html을 반환하는 것이 아닌, http의 response body부에 return 값을 직접 넣어줌
	@GetMapping("hello-string")
	@ResponseBody
	public String helloString(@RequestParam("name") String name) {
		return "string : " + name;
	}
	
//	5. → [웹 브라우저]로 hello 객체를 Json 형식{키:값}인 {"name":"abc"}로 응답
	@GetMapping("hello-api")
	@ResponseBody
	public Hello helloApi(@RequestParam("name") String name) {
		
		Hello hello = new Hello();
		hello.setName(name);
		return hello;
	}
	
	static class Hello {	// Hello 객체 생성
		private String name;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
