package io.yichwen.respository;

import io.yichwen.model.PostalCodeLatitudeLongitude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostalCodeLatitudeLongitudeRepository extends JpaRepository<PostalCodeLatitudeLongitude, Long> {
    PostalCodeLatitudeLongitude findByPostcode(String postcode);
}
