package com.example.docmenuservice.controller;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/docs")
@Tag(name = "Document Menu - Service")
@CrossOrigin
public class DocMenuController {

//    @PostMapping("")
//    @Operation(summary = "set up shop")
//    @SecurityRequirement(name = "oAuth2")
//    public ResponseEntity<ApiResponse<ShopResponse>> setUpShop(@Valid @RequestBody ShopRequest request) throws Exception {
//        return new ResponseEntity<>(new ApiResponse<>(
//                "Shop has set up successfully",
//                shopService.setUpShop(request),
//                HttpStatus.CREATED
//        ), HttpStatus.CREATED);
//    }

//    @GetMapping("")
//    @Operation(summary = "fetch all shops")
//    public ResponseEntity<ApiResponse<List<ShopResponse>>> getAllShop(){
//        return new ResponseEntity<>(new ApiResponse<>(
//                "Shops fetched successfully",
//                shopService.getAllShop(),
//                HttpStatus.OK
//        ), HttpStatus.OK);
//    }
}
