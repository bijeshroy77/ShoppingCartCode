package com.shoppingcart.apps.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

//import com.shoppingcart.apps.entity.Product;
import com.shoppingcart.apps.model.ProductInfo;
import com.shoppingcart.apps.model.User;

@Controller

public class ShoppingCartController {


	//@Autowired
	//RestTemplate restTemplate;

	@Value("${login.service.url}")
	private String loginServiceURL;

	@Value("${product.service.url}")
	private String productCatalogURL;

	@RequestMapping("/ShoppingCart/")
	public String welcome(Map<String, Object> model) {
		System.out.println("Inside Init");		
		return "index";
	}
	
	@RequestMapping("/")
	public String home(Map<String, Object> model) {
		System.out.println("Inside Init");
		
		return "index";
	}
	

	// GET: Show Login Page
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login(Model model) {

		return "login";
	}

	@RequestMapping(value = { "/accountInfo" }, method = RequestMethod.GET)
	public String accountInfo(HttpServletRequest request,Model model) {

		User user=(User) request.getSession().getAttribute("user");      

		model.addAttribute("user", user);
		return "accountInfo";
	}

	/* @RequestMapping(value = { "/orderList" }, method = RequestMethod.GET)
    public String orderList(Model model, //
            @RequestParam(value = "page", defaultValue = "1") String pageStr) {
        int page = 1;
        try {
            page = Integer.parseInt(pageStr);
        } catch (Exception e) {
        }
        final int MAX_RESULT = 5;
        final int MAX_NAVIGATION_PAGE = 10;

        PaginationResult<OrderInfo> paginationResult //
        = orderDAO.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);

        model.addAttribute("paginationResult", paginationResult);
        return "orderList";
    }*/

	// GET: Show product.
	@RequestMapping(value = { "/product" }, method = RequestMethod.GET)
	public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
		ProductInfo productInfo = null;
		if (code != null && code.length() > 0) {
			System.out.println("Product Code="+code);
			MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
			params.add("code", code);				
			UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(productCatalogURL+"/productList").queryParams(params).build();
			System.out.println("uriComponents="+uriComponents.toUriString());		
			RestTemplate restTemplate=new RestTemplate();			
			productInfo=restTemplate.getForObject(uriComponents.toUri().toString(), ProductInfo.class,"200");
		}

		if (productInfo == null) {
			productInfo = new ProductInfo();
			productInfo.setNewProduct(true);
		}
		model.addAttribute("productForm", productInfo);
		return "product";
	}
	
	 // POST: Save product
    @RequestMapping(value = { "/product" }, method = RequestMethod.POST)
    // Avoid UnexpectedRollbackException (See more explanations)
    //@Transactional(propagation = Propagation.NEVER)
    public String productSave(Model model, //
            @ModelAttribute("productForm") @Validated ProductInfo productInfo, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {
 
    		System.out.println("Inside productSave ");
    	MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		params.add("code", productInfo.getCode());
		params.add("name", productInfo.getName());
		params.add("price", productInfo.getPrice());
		params.add("fileData", productInfo.getFileData());
				
		System.out.println("Calling save ");
		RestTemplate restTemplate=new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
        ResponseEntity<String> responseEntity =  restTemplate.exchange((productCatalogURL+"/saveProduct"),HttpMethod.POST, requestEntity, String.class);
        System.out.println("Response received "+responseEntity.getBody());
        if (responseEntity.getBody().equalsIgnoreCase("failure")) {
            return "product";
        }
        /*try {
            productDAO.save(productInfo);
        } catch (Exception e) {
            // Need: Propagation.NEVER?
            String message = e.getMessage();
            model.addAttribute("message", message);
            // Show product form.
            return "product";
 
        }*/
        return "redirect:/productList";
    }

	

	@RequestMapping(value = { "/validateUser" }, method = RequestMethod.POST)
	public String validateUser(HttpServletRequest request,Model model, @RequestParam("userName") String userName,@RequestParam("password") String password) {
		
		System.out.println("UserName="+userName);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("userName", userName);
		params.add("password", password);		
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(loginServiceURL+"/validateUser").queryParams(params).build();
		System.out.println("uriComponents="+uriComponents.toUriString());		
		RestTemplate restTemplate=new RestTemplate();
		User user= restTemplate.getForObject(uriComponents.toUri().toString(),User.class,params);
		if((user!=null) && (user.getUserName()!=null)){
			model.addAttribute("userPrincipal", user);			
			request.getSession().setAttribute("user", user);
			return "index";

		}else{
			model.addAttribute("InvalidUser", "Invalid username or password");
			return "login";
		}
		
		
	}

	@RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
	public String logout(HttpServletRequest request,Model model) {
		request.getSession().removeAttribute("user");
		request.getSession().invalidate();   	 

		return "index";
	}

	@RequestMapping({ "/productList" })
	public String getProductList(Model model) {
		//final int maxResult = 5;
		// final int maxNavigationPage = 10;

		//String url=createLoginUrl(loginServiceURL,"validateUser",userName,password);
		RestTemplate restTemplate=new RestTemplate();
		//ProductInfo productInfo=restTemplate.getForObject((productCatalogURL+"/productList"), ProductInfo.class,"200");
		ResponseEntity<List<ProductInfo>> rateResponse =
		        restTemplate.exchange((productCatalogURL+"/productList"),HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductInfo>>() {
		            });
		List<ProductInfo> products=rateResponse.getBody();
		System.out.println("product"+products);

		model.addAttribute("Products", products);
		return "productList";
	}
	
	@RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
	public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("code") String code) throws IOException {
		
		System.out.println("Product Code="+code);
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("code", code);
		//params.add("password", password);		
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(productCatalogURL+"/productImage").queryParams(params).build();
		System.out.println("uriComponents="+uriComponents.toUriString());		
		RestTemplate restTemplate=new RestTemplate();
		byte[] images= (byte[]) restTemplate.getForObject(uriComponents.toUri().toString(),byte[].class,params);
		
		/*model.addAttribute("userPrincipal", user);
		request.getSession().setAttribute("user", user);*/
		if (images != null && images.length>0) {
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif;charset=UTF-8");
			response.getOutputStream().write(images);
		}
		response.getOutputStream().close();
	}
	
	

	

}