package ar.edu.itba.paw.cryptuki.form;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RatingForm {
    @NotNull
    @Min(1)
    @Max(10)
    private Integer rating;

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
