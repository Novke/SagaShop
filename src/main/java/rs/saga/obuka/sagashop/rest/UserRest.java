package rs.saga.obuka.sagashop.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import rs.saga.obuka.sagashop.domain.User;
import rs.saga.obuka.sagashop.dto.user.CreateUserCmd;
import rs.saga.obuka.sagashop.dto.user.UpdateUserCmd;
import rs.saga.obuka.sagashop.dto.user.UserInfo;
import rs.saga.obuka.sagashop.dto.user.UserResult;
import rs.saga.obuka.sagashop.exception.ServiceException;
import rs.saga.obuka.sagashop.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRest {

    private final UserService userService;

    @PostMapping("/save")
    public User save(@RequestBody @Valid CreateUserCmd cmd) throws ServiceException {
        return userService.save(cmd);
    }

    @GetMapping("/all")
    @ResponseBody
    public List<UserResult> findAll() {
        return userService.findAll();
    }

    @GetMapping("/id/{id}")
    public UserInfo findById(@PathVariable long id) {
        return userService.findById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UpdateUserCmd cmd) throws ServiceException {
        userService.update(cmd);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws ServiceException {
        userService.delete(id);
    }

}
