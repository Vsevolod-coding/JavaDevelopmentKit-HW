package server.repository;

/**
 * The Repository interface defines the contract for a generic data storage mechanism.
 * It provides methods to save and load data as well as to check the existence of the storage file.
 *
 * @param <E> the type of data to be handled by the repository.
 */
public interface Repository<E> {
    /**
     * Saves the provided data to the repository.
     *
     * @param data the data to be saved.
     */
    void save(E data);

    /**
     * Loads and returns the data from the repository.
     *
     * @return the loaded data of type E.
     */
    E load();

    /**
     * Checks for the existence of the repository storage file and creates it if it does not exist.
     */
    void checkHistoryFile();
}
