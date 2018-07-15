package urjc.ist.playlist;

import java.util.Objects;

public class Cancion {
	
	public enum Codecs {	// Tipo enumerado, 
		MP3, FLAC, OGG		// solo pueden usarse esos valores y no otros.
	}

	private String titulo;  // Título de la canción
	private String autor;  // Autor(a) de la canción
	private int duracion;  // Duración en segundos
	private Codecs formato;  // Codificación de la canción
	
	public Cancion() {
		// TODO Auto-generated constructor stub
		titulo = "";
		autor = "";
		duracion = 0;
		formato = Codecs.MP3;
	}
	
	public Cancion(String titulo, String autor, int duracion, Codecs format) {
		this.titulo = titulo;
		this.autor = autor;
		this.duracion = duracion;
		this.formato = format;
	}
	
	public String getTitulo(){
		return titulo;
	}
	
	public void setTitulo(String titulo){
		this.titulo = titulo;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public int getDuracion() {
		return duracion;
	}
	
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
	public Codecs getFormato() {
		return formato;
	}
	
	public void setFormato(Codecs formato) {
		this.formato = formato;
	}
	
	@Override	// Indica que el siguiente metodo pretende sobreescribir el metodo toString de la superclase
	public String toString() {
		/**
		 * Creación de una representación del contenido de la
		 * Canción en formato String
		 */
		int hours = duracion / 3600;
		int minutes = (duracion % 3600) / 60;
		int seconds = duracion % 60;

		String timeString;
		if (hours > 0) {
			timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		} else {
			timeString = String.format("%02d:%02d", minutes, seconds);
		}
		String formatoString;
		switch (formato) {
			case MP3: 
				formatoString = "MP3";
				break;
			case FLAC: 
				formatoString = "FLAC";
				break;
			case OGG:
				formatoString = "OGG";
				break;
			default:
				formatoString = "N/A";
				break;
		}
		return String.join("\n", "----------",
				"Título: " + titulo,
				"Autor: " + autor,
				"Duración: " + timeString,
				"Formato: " + (formato == Codecs.MP3 ? "MP3" : "FLAC"),
				"----------");
	}
	
	@Override
	public boolean equals(Object other) {	//Compara si objeto A es igual a objeto B
		/**
		 * Implementación de un método de comparación del contenido
		 * de dos canciones
		 */
		// Estos 3 metodos estan presentes en todo metodo equal
		if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Cancion)) return false;			
	    Cancion otherCancion = (Cancion)other;
	    if (this.titulo == otherCancion.titulo &&
	    		this.autor == otherCancion.autor &&
	    		this.duracion == otherCancion.duracion &&
	    		this.formato == otherCancion.formato) {
	    	return true;
	    } else {
	    	return false;
	    }
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(titulo, autor, duracion, formato);
	}
	
	public static void main(String[] args) {
		Cancion track1 = new Cancion("The Song of the Sun", "Mike Olfield", 273, Codecs.MP3);
		Cancion track2 = new Cancion("The Song of the Sun", "Mike Olfield", 273, Codecs.MP3);
		System.out.println(track1);
		System.out.println(track1.equals(track2));
	}

}
