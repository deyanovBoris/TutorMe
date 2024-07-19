package bg.softuni.tutorme.web;

import bg.softuni.tutorme.entities.dtos.AddPostingDTO;
import bg.softuni.tutorme.service.PostingService;
import bg.softuni.tutorme.service.UserEntityService;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class PostingController {

    private final PostingService postingService;
    private final UserEntityService userEntityService;

    public PostingController(PostingService postingService, UserEntityService userEntityService) {
        this.postingService = postingService;
        this.userEntityService = userEntityService;
    }

    @ModelAttribute("addPostData")
    public AddPostingDTO addPostingDTO(){
        return new AddPostingDTO();
    }

    @GetMapping("/post/add-post")
    public String addPost(){

        return "add-post";
    }

    @PostMapping("/post/add-post")
    public String doAddPost(@Valid AddPostingDTO addPostingDTO, Principal principal) throws UserNotFoundException {
        addPostingDTO.setUserId(Long.parseLong(this.userEntityService.getUserIdByUsername(principal.getName())));
        this.postingService.createPost(addPostingDTO);

        return "redirect:/";
    }
}
