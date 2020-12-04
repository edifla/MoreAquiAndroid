package br.com.rafaelalbuquerque.moreaquix;
//Servidor

public interface Command {
    /**
     * Performs a series of actions on the input object.
     *
     * @param d the subject of the actions that will be executed.
     */
    void execute(DaoImpl d);
}
