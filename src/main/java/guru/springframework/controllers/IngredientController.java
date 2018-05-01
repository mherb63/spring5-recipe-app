package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("recipe/{id}/ingredients")
    public String getIngredientsByRecipeId(@PathVariable String id, Model model) {
        log.debug("Getting ingredients for recipe id: " + id);

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("recipe/ingredient/{id}/show")
    public String showIngredient(@PathVariable String id, Model model) {
        log.debug("Getting ingredient for id: " + id);

        model.addAttribute("ingredient", ingredientService.findIngredientCommandById(Long.valueOf(id)));
        return "recipe/ingredient/show";

    }

    @GetMapping
    @RequestMapping("recipe/ingredient/{id}/update")
    public String updateIngredient(@PathVariable String id, Model model) {
        model.addAttribute("ingredient", ingredientService.findIngredientCommandById(Long.valueOf(id)));
        model.addAttribute("uomList", unitOfMeasureService.getAllUoms());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping
    @RequestMapping("recipe/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedCommmand = ingredientService.saveIngredientCommand(ingredientCommand);

        log.debug("saved Ingredient id: " + savedCommmand.getId() + " with Recipe id: " + savedCommmand.getRecipeId());

        return "redirect:/recipe/ingredient/" + savedCommmand.getId() + "/show";
    }

}
