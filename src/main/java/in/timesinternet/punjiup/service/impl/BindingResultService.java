package in.timesinternet.punjiup.service.impl;
import in.timesinternet.punjiup.exception.InvalidRequestBodyException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import java.util.ArrayList;
import java.util.List;
@Service
public class BindingResultService {

    public void validate(BindingResult bindingResult) {
        List<String> errorMessages = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> errorMessages.add(objectError.getDefaultMessage()));
            throw new InvalidRequestBodyException(errorMessages.toString());
        }

    }
}