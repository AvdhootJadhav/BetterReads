package io.javabrains.betterreads.userbooks;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserBooksController {
    
    @Autowired UserBooksRepository userBooksRepository;

    @PostMapping("/addUserBook")
    private ModelAndView addBookForUser(@RequestBody MultiValueMap<String,String> formData,@AuthenticationPrincipal OAuth2User principal){
        
        UserBooks userbook = new UserBooks();
        UserBooksPrimaryKey key = new UserBooksPrimaryKey();
        
        if(principal == null || principal.getAttribute("login") == null){
            return null;
        }
        
        key.setUserId(principal.getAttribute("login"));
        String bookId = formData.getFirst("bookId");
        key.setBookId(bookId);
        userbook.setKey(key);

        userbook.setStartedDate(LocalDate.parse(formData.getFirst("startDate")));
        userbook.setCompletedDate(LocalDate.parse(formData.getFirst("completedDate")));
        userbook.setRating(Integer.parseInt(formData.getFirst("rating")));
        userbook.setReadingStatus(formData.getFirst("readingStatus"));

        userBooksRepository.save(userbook);
        return new ModelAndView("redirect:/books/"+ bookId);
    }
}