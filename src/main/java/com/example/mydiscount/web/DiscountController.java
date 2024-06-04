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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.example.mydiscount.JavaCornerConstants.*;

@Controller
@RequestMapping(value = "/discounts")
public class DiscountController {
    private DiscountService discountService;
    private DiscountTypeService discountTypeService;
    private UserService userService;
    private UserDiscountService userDiscountService;

    public DiscountController(DiscountService discountService, DiscountTypeService discountTypeService, UserService userService, UserDiscountService userDiscountService) {
        this.discountService = discountService;
        this.discountTypeService = discountTypeService;
        this.userService = userService;
        this.userDiscountService = userDiscountService;
    }

    @GetMapping("/index")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String index(Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword) {
        List<Discount> discounts = discountService.findDiscountsByCode(keyword);
        model.addAttribute(LIST_DISCOUNTS, discounts);
        model.addAttribute(KEYWORD, keyword);
        return "discount-views/discounts";
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteDiscount(Long discountId, String keyword) {
        discountService.removeDiscount(discountId);
        return "redirect:/discounts/index?keyword=" + keyword;
    }

    @GetMapping(value = "/formUpdate")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateDiscount(Model model, Long discountId) {

        Discount discount = discountService.loadDiscountById(discountId);
        List<DiscountType> discountTypes = discountTypeService.fetchAll();
        model.addAttribute(LIST_DISCOUNT_TYPES, discountTypes);
        model.addAttribute(DISCOUNT, discount);
        return "discount-views/formUpdate";
    }

    @GetMapping(value = "/formCreate")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String formDiscounts(Model model, Principal principal) {
        List<DiscountType> discountTypes = discountTypeService.fetchAll();
        model.addAttribute(LIST_DISCOUNT_TYPES, discountTypes);
        model.addAttribute(DISCOUNT, new Discount());
        return "discount-views/formCreate";
    }

    @PostMapping(value = "/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveDiscount(@ModelAttribute("discount") Discount discount) {
        discountService.upsertDiscount(discount);
        return "redirect:/discounts/index";
    }

    @GetMapping(value = "/index/user/searchByPercentage")
    @PreAuthorize("hasAuthority('USER')")
    public String tests(Model model, @RequestParam(name = PERCENTAGE, defaultValue = "0") Integer percentage) {
        model.addAttribute(PERCENTAGE, percentage);
        List<Discount> discounts = discountService.fetchAllWithActiveStatusDiscounts();
        List<Discount> otherDiscounts = discountService.fetchAll();
        model.addAttribute(LIST_DISCOUNTS, discounts);
        model.addAttribute(OTHER_DISCOUNTS, otherDiscounts);
        return "discount-views/user-discounts";
    }

    @GetMapping(value = "/index/user")
    @PreAuthorize("hasAuthority('USER')")
    public String discountsForCurrentUser(Model model, Principal principal, @RequestParam(name = PERCENTAGE, defaultValue = "0") Integer percentage) {
        //TODO find all discounts here with the percentage > than the parameter value and use that array to filter out more
        User user = userService.loadUserByEmail(principal.getName());
        List<DiscountType> discountTypes = discountTypeService.fetchAll();
        List<Discount> discounts = discountService.fetchActiveDiscountsForUserWithPercentage(user.getUserId(), percentage);
        List<UserDiscount> activeCustomerDiscounts = userDiscountService.fetchUserDiscountsByUserId(user.getUserId());
        List<Discount> otherDiscounts = discountService.fetchAllWithActiveStatusDiscounts(percentage);

        if (!CollectionUtils.isEmpty(discounts)) {
            if (percentage > 0) {
                otherDiscounts = discountService.fetchAllWithPercentage(percentage).stream().filter(discount -> !discounts.contains(discount) && discount.isStatus()).toList();
            } else {
                otherDiscounts = discountService.fetchAll().stream().filter(discount -> !discounts.contains(discount) && discount.isStatus()).toList();
            }
        }
        model.addAttribute(LIST_DISCOUNTS, discounts);
        model.addAttribute(OTHER_DISCOUNTS, otherDiscounts);
        model.addAttribute(NAME, user.getName());
        model.addAttribute(LIST_DISCOUNT_TYPES, discountTypes);
        model.addAttribute("userDiscount", new UserDiscount());
        model.addAttribute("listActiveCustomerDiscounts", activeCustomerDiscounts);

        model.addAttribute(PERCENTAGE, percentage);

        return "discount-views/user-discounts";
    }

    @GetMapping(value = "/activate")
    @PreAuthorize("hasAuthority('USER')")
    public String markAsWatched(Long discountId, Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());
        discountService.activate(user.getUserId(), discountId);
        return "redirect:/discounts/index/user";
    }

    @GetMapping(value = "/deactivate")
    @PreAuthorize("hasAuthority('USER')")
    public String removeFromWatched(Long discountId, Principal principal) {
        User user = userService.loadUserByEmail(principal.getName());
        discountService.deactivate(user.getUserId(), discountId);
        return "redirect:/discounts/index/user";
    }


}
