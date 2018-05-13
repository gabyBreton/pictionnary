package projet.pictionnary.breton.server.dto;

/**
 * Class used a dto for the Word table.
 * 
 * @author Gabriel Breton - 43397
 */
public class WordDto extends EntityDto<Integer> {
    
    private final String word;
    
    /**
     * Constructs a new WordDto.
     * 
     * @param word the word to transfer.
     */
    public WordDto(String word) {
        this.word = word;
    }

    /**
     * Gives the word.
     * 
     * @return the word.
     */
    public String getWord() {
        return word;
    }
}
