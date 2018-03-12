package projet.pictionnary.breton.model;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class WordCheck {
    
    private String toGuess;
    private String proposal;

    public WordCheck() {
        this.toGuess = "Informatique";
        this.proposal = "";
    }

    public String getToGuess() {
        return toGuess;
    }

    public String getProposal() {
        return proposal;
    }

    public void setToGuess(String toGuess) {
        this.toGuess = toGuess;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }
    
    public boolean equals() {
        return toGuess.equalsIgnoreCase(proposal);
    }
}
