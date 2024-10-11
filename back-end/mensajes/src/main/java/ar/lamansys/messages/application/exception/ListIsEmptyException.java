package ar.lamansys.messages.application.exception;


public class ListIsEmptyException extends Throwable{

    public ListIsEmptyException() {
        super(String.format("La lista ingresada esta vacia"));
    }
}
