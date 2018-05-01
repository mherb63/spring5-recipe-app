package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findIngredientCommandById(Long id);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
