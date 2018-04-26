package projet.pictionnary.breton.server.dto;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class WordDto extends EntityDto<Integer> {
    
    private final String word;
    
    public WordDto(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}
