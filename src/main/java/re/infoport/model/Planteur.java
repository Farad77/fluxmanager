package re.infoport.model;


import java.util.List;

public class Planteur extends Agent {

	

	private List<Agent> agents;
	public List<Agent> getAgents() {
		
		if(agents!=null&&agents.contains(this)) agents.remove(this);
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}
	public String getNumeroPlanteur() {
		return numeroPlanteur;
	}

	public void setNumeroPlanteur(String numeroPlanteur) {
		this.numeroPlanteur = numeroPlanteur;
	}

	private String numeroPlanteur;

	private String nomResponsable;

	private String nomSocial;

	public Planteur() {

	}

	public String getNomResponsable() {
		return nomResponsable;
	}

	public void setNomResponsable(String nomResponsable) {
		this.nomResponsable = nomResponsable;
	}

	public String getNomSocial() {
		return nomSocial;
	}

	public void setNomSocial(String nomSocial) {
		this.nomSocial = nomSocial;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "(Planteur) "+this.getNom()+" "+this.getPrenom();
	}
}
