package fr.cocoraid.babouinmalin.exceptions;

public class BarterNotFoundException extends Exception {
        public BarterNotFoundException(String barterId) {
                super(String.format("Barter is not found with id : '%s'", barterId));
        }
}