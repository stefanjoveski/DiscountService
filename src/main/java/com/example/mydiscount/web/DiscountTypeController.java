package com.example.mydiscount.web;

import com.example.mydiscount.soap.Discount;
import com.example.mydiscount.entity.DiscountType;
import com.example.mydiscount.entity.User;
import com.example.mydiscount.entity.UserDiscount;
import com.example.mydiscount.service.DiscountService;
import com.example.mydiscount.service.DiscountTypeService;
import com.example.mydiscount.service.UserDiscountService;
import com.example.mydiscount.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

import static com.example.mydiscount.JavaCornerConstants.*;

@Controller
@RequestMapping(value = "/discount-type")
public class DiscountTypeController {

    private DiscountTypeService discountTypeService;
    private UserService userService;
    private UserDiscountService userDiscountService;
    private DiscountService discountService;

    public DiscountTypeController(DiscountTypeService discountTypeService, UserService userService, UserDiscountService userDiscountService, DiscountService discountService) {
        this.discountTypeService = discountTypeService;
        this.userService = userService;
        this.userDiscountService = userDiscountService;
        this.discountService = discountService;
    }

    @GetMapping("/index")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String index(Model model, @RequestParam(value = KEYWORD, defaultValue = "") String keyword) {
        List<DiscountType> discountTypes = discountTypeService.findDiscountTypeByCategory(keyword);
        model.addAttribute(LIST_DISCOUNT_TYPES, discountTypes);
        model.addAttribute(KEYWORD, keyword);
        return "discount-types-views/discount-types";
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteDiscountTypes(Long discountTypeId, String keyword) {
        discountTypeService.removeDiscountType(discountTypeId);
        return "redirect:/discount-type/index?keyword=" + keyword;
    }

    @GetMapping(value = "/formUpdate")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateDiscountType(Model model, Long discountTypeId) {
        DiscountType discountType = discountTypeService.loadDiscountTypeById(discountTypeId);
        model.addAttribute(DISCOUNT_TYPE, discountType);
        return "discount-types-views/formUpdate";
    }

    @GetMapping(value = "/formCreate")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String formDiscountType(Model model, Principal principal) {
        model.addAttribute(DISCOUNT_TYPE, new DiscountType());
        return "discount-types-views/formCreate";
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveDiscountType(DiscountType discountType) {
        discountTypeService.upsertDiscountType(discountType);
        return "redirect:/discount-type/index";
    }

    @GetMapping(value = "/index/user")
    @PreAuthorize("hasAuthority('USER')")
    public String userDiscountTypes(Model model, Long discountTypeId, Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());

        List<UserDiscount> userDiscountsForUserId = userDiscountService.fetchCurrentActiveDiscountsByUserId(user.getUserId());
        List<Long> activeDiscountIds = userDiscountsForUserId.stream()
                .map(userDiscount -> userDiscount.getDiscount()
                .getDiscountId())
                .toList();

        List<DiscountType> allDiscountTypes = discountTypeService.fetchWithAtLeastOneDiscount();
        // User that has all discounts activated from a specific category
        List<DiscountType> activeUserDiscountTypes = allDiscountTypes.stream()
                .filter(discountType -> discountType.getDiscounts().stream()
                        .filter(Discount::isStatus)
                        .allMatch(discount -> activeDiscountIds.contains(discount.getDiscountId())))
                .toList();

        List<DiscountType> otherAvailableDiscountTypes = allDiscountTypes.stream()
                .filter(discountType -> !activeUserDiscountTypes.contains(discountType))
                .toList();

        model.addAttribute(NAME, user.getName());
        model.addAttribute(LIST_DISCOUNT_TYPES, activeUserDiscountTypes);
        model.addAttribute(OTHER_DISCOUNT_TYPES, otherAvailableDiscountTypes);

        return "discount-types-views/user-discount-types";
    }

    @GetMapping(value = "/bulkActivation")
    @PreAuthorize("hasAuthority('USER')")
    public String bulkActivation(Model model, Long discountTypeId, Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());
        DiscountType discountType = discountTypeService.loadDiscountTypeById(discountTypeId);
        for (Discount discount : discountType.getDiscounts()) {
            if (userDiscountService.isNotPresentAndStatusIsActive(user.getUserId(), discount.getDiscountId())) {
                userDiscountService.activate(user.getUserId(), discount.getDiscountId());
            }
        }
        model.addAttribute(NAME, user.getName());
        model.addAttribute(LIST_DISCOUNT_TYPES, discountType);

        return "redirect:/discount-type/index/user";
    }

    @GetMapping(value = "/bulkDeactivation")
    @PreAuthorize("hasAuthority('USER')")
    public String discountsForCurrentUser(Model model, Long discountTypeId, Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());
        DiscountType discountType = discountTypeService.loadDiscountTypeById(discountTypeId);
        for (Discount discount : discountType.getDiscounts()) {
            if (!userDiscountService.isNotPresentAndStatusIsActive(user.getUserId(), discount.getDiscountId())) {
                userDiscountService.deactivate(user.getUserId(), discount.getDiscountId());
            }
        }
        model.addAttribute(NAME, user.getName());
        model.addAttribute(LIST_DISCOUNT_TYPES, discountType);
        return "redirect:/discount-type/index/user";
    }
}
