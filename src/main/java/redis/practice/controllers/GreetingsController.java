package redis.practice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = { "/", "" })
public class GreetingsController {

    @Autowired
    @Qualifier("myConfig")
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping
    public String getGreetings(Model model) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String greetings = ops.get("greetings");
        model.addAttribute("greetings", greetings);
        return "index";
    }

    @PostMapping
    public String postMessage(@RequestBody MultiValueMap<String, String> form, Model model) {
        redisTemplate.opsForValue().set("greetings", form.getFirst("newmsg"));

        // // To see db reflected:
        String newPage = getGreetings(model);
        return newPage;

        // For more instantaneous results:
        // ValueOperations<String, String> ops = redisTemplate.opsForValue();
        // String greetings = ops.get("greetings");
        // model.addAttribute("greetings", greetings);
        // return "index";
    }
}
