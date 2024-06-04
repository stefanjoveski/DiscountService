package com.example.mydiscount.runner;

import com.example.mydiscount.soap.Discount;
import com.example.mydiscount.entity.DiscountType;
import com.example.mydiscount.entity.User;
import com.example.mydiscount.service.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Random;

@Component
public class MyRunner implements CommandLineRunner {
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    private UserService userService;
    private DiscountTypeService discountTypeService;
    private DiscountService discountService;

    public MyRunner(UserService userService, DiscountTypeService discountTypeService, DiscountService discountService) {
        this.userService = userService;
        this.discountService = discountService;
        this.discountTypeService = discountTypeService;
    }

    @Override
    public void run(String... args) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        User user1 = userService.createUser("user1@gmail.com", "pass1", USER, "user1");
        User user2 = userService.createUser("user2@gmail.com", "pass1", USER, "user2");
        User admin = userService.createUser("admin@gmail.com", "pass2", ADMIN, "admin1");


        DiscountType discountType = discountTypeService.createDiscountType("Health");
        DiscountType discountType2 = discountTypeService.createDiscountType("Fruits");
        DiscountType discountType3 = discountTypeService.createDiscountType("Clothes");

        Discount discount1 = discountService.createDiscount(randomStringGenerator(), 50, format.parse("03/06/2023"), format.parse("23/06/2023"), true, discountType.getDiscountTypeId());
        Discount discount2 = discountService.createDiscount(randomStringGenerator(), 30, format.parse("05/06/2023"), format.parse("23/06/2023"), false, discountType.getDiscountTypeId());
        Discount discount3 = discountService.createDiscount(randomStringGenerator(), 25, format.parse("06/06/2023"), format.parse("12/06/2023"), true, discountType.getDiscountTypeId());
        Discount discount4 = discountService.createDiscount(randomStringGenerator(), 90, format.parse("05/06/2023"), format.parse("10/06/2023"), true, discountType2.getDiscountTypeId());


        discountService.activate(user1.getUserId(), discount1.getDiscountId());
        discountService.activate(user2.getUserId(), discount1.getDiscountId());

    }


    public String randomStringGenerator() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}