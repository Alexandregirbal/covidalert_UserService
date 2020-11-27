package polytech.covidalert.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import polytech.covidalert.exception.FormNotCompletedException;
import polytech.covidalert.exception.ResourceAlreadyExistsException;
import polytech.covidalert.exception.ResourceNotFoundException;
import polytech.covidalert.models.User;
import polytech.covidalert.models.UserRepository;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/covidalert/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public List<User> list() {
        return userRepository.findAll();
    }

   /* @GetMapping
    @RequestMapping("{id}")
    public User get(@PathVariable Long id) {
        if (! userRepository.findById(id).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with ID " +id+ " not found.");
        }
        return userRepository.getOne(id);
    }*/

    @GetMapping
    @RequestMapping("{email}")
    public User getUserByEmail(@PathVariable String email) {
        if ( userRepository.findByEmail(email) == null){
            throw new ResourceNotFoundException(HttpStatus.INTERNAL_SERVER_ERROR, "User with email " +email+ " not found.");
        }
        return userRepository.findByEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User create(@RequestBody final User user) {
        // Si l'user existe le retourner
        if ( userRepository.findByEmail(user.getEmail()) != null){
           throw new ResourceAlreadyExistsException(HttpStatus.INTERNAL_SERVER_ERROR, "User with email " + user.getEmail()+ " already exist.");
        }
        return userRepository.saveAndFlush(user);
    }

    @RequestMapping(value = "{email}",method = RequestMethod.DELETE)
    public void delete(@PathVariable String email) {
        // FAIRE UNE REQUETE VERS CHAQUE SERVICE POUR SUPPRIMERS les JOINs
        userRepository.deleteByEmail(email);
    }

    @RequestMapping(value="{email}",method = RequestMethod.PUT)
    public User update(@PathVariable String email, @RequestBody User user) {
        User existingUser = userRepository.findByEmail(email);
        BeanUtils.copyProperties(user, existingUser, "user_id");
        return userRepository.saveAndFlush(existingUser);
    }
}
