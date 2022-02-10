package lift.off.project.models.data;

import lift.off.project.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);

  //  @Query (from User user where user.registeredType={})
    //User findByRegisteredType(String registeredType);

}