package bg.softuni.tutorme.web;

import bg.softuni.tutorme.entities.dtos.posts.AddPostingDTO;
import bg.softuni.tutorme.service.PostingService;
import bg.softuni.tutorme.service.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping("/post/add-post")
    public String doAddPost(@Valid AddPostingDTO addPostingDTO, Principal principal) {
        this.postingService.createPost(addPostingDTO, principal);

        return "redirect:/posts/all";
    }

    @GetMapping("/posts/all")
    public String allPosts(Model model){
        model.addAttribute("postsPageStyling", true);
        model.addAttribute("posts", this.postingService.getAllPosts());

        return "posts";
    }


}
