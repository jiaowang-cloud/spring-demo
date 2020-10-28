package yooo.yun.com.user;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangJiao
 * @since 2020/10/14
 */
@RequestMapping(value = "/saas/user")
@RestController("userC")
public class UserController {
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") long id) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("msg", "user 模块");
        return jsonObject.toString();
    }
}
