
package com.bootlabs.demo.repositories;

import com.bootlabs.demo.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <h2>AuthorRepository</h2>
 * Description: Spring Data repository for the {@link Author} entity.
 * 
 * @author @bootteam
 */
@Repository
public interface AuthorRepository  extends JpaRepository<Author, Long> {

}
