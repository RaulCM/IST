package urjc.ist.playlist;

import java.util.ArrayList;
import urjc.ist.playlist.Cancion;
import urjc.ist.playlist.Cancion.Codecs;
import java.util.Objects;


public class Album {
	
	private String titulo;
	private String autor;
	private String grupo;
	private int duracionTotal;
	private ArrayList<Cancion> trackList;

	public Album() {
		titulo = "";
		autor = "";
		grupo = "";
		duracionTotal = 0;
	}
	
	public Album(String titulo, String autor, String grupo) {
		this.titulo = titulo;
		this.autor = autor;
		this.grupo = grupo;
		duracionTotal = 0;
		trackList = new ArrayList<Cancion>();
	}
	
	public ArrayList<Cancion> getTrackList() {
		/**
		 * Método que devuelve la lista de canciones actualmente
		 * incluídas en el álbum
		 */
		return trackList;
	}
	
	public Cancion getTrack(int posicion) {
		/**
		 * Método que devuelve la canción que esté en la posición
		 * de la lista del álbum que se indica como argumento
		 */
		try{
			return trackList.get(posicion);
		}
		catch(IndexOutOfBoundsException exception){
			System.out.println("Capturada la excepción:" + exception);
			return null;
		}
	}
	
	public void addTrack(Cancion unaCancion) {
		/**
		 * Método que añade una canción a la lista de canciones
		 * del álbum. Además, el método calcula y actualiza
		 * automáticamente la nueva duración total del álbum
		 */
		trackList.add(unaCancion);
		duracionTotal = duracionTotal + unaCancion.getDuracion();
	}
	
	public void addTrack(int posicion, Cancion unaCancion) {
		/**
		 * Método que añade una canción al álbum(que pasamos
		 * como segundo argumento) en la posición que indique
		 * el int que recibe como primer argumento
		 */
		trackList.add(posicion, unaCancion);
		duracionTotal = duracionTotal + unaCancion.getDuracion();
	}
	
	public void deleteLastTrack() {
		/**
		 * Método que borra la última canción de la lista del
		 * álbum
		 */
		try{
			duracionTotal = duracionTotal - trackList.get(trackList.size() - 1).getDuracion();
			trackList.remove(trackList.size() - 1);
		}
		catch(IndexOutOfBoundsException exception){
			System.out.println("Capturada la excepción:" + exception);
		}
	}
	
	public void deleteTrack(int posicion) {
		/**
		 * Método que borra la canción en la posición de la lista
		 * que indica el argumento que recibe
		 */
		try{
			duracionTotal = duracionTotal - trackList.get(posicion).getDuracion();
			trackList.remove(posicion);
		}
		catch(IndexOutOfBoundsException exception){
			System.out.println("Capturada la excepción:" + exception);
		}
	}

	public void clearAlbum() {
		/**
		 * Método que borra todas las canciones en la lista de
		 * un álbum
		 */
		duracionTotal = 0;
		trackList.clear();
	}
	
	public String getTitulo(){
		return titulo;
	}
	
	public void setTitulo(String titulo){
		this.titulo = titulo;
	}
	
	public String getAutor(){
		return autor;
	}
	
	public void setAutor(String autor){
		this.autor = autor;
	}
	
	public String getGrupo(){
		return grupo;
	}
	
	public void setGrupo(String grupo){
		this.grupo = grupo;
	}

	public int getDuracionTotal() {
		return duracionTotal;
	}
	
	public void setDuracionTotal(int duracionTotal){
		this.duracionTotal = duracionTotal;
	}
	
	@Override
	public String toString() {
		int hours = duracionTotal / 3600;
		int minutes = (duracionTotal % 3600) / 60;
		int seconds = duracionTotal % 60;

		String timeString;
		if (hours > 0) {
			timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		} else {
			timeString = String.format("%02d:%02d", minutes, seconds);
		}

		return String.join("\n", "----------",
				"Título del álbum: " + titulo,
				"Autor: " + autor,
				"Grupo: " + grupo,
				"Duración: " + timeString,
				"----------",
				"Lista de Canciones:\n" + trackList);
	}
	
	@Override
	public boolean equals(Object other) {
		/**
		 * Implementación de un método de comparación del contenido
		 * de dos albumes
		 */
		if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Album)) return false;
	    Album otherAlbum = (Album)other;
	    if (this.titulo == otherAlbum.titulo &&
	    		this.autor == otherAlbum.autor &&
	    		this.grupo == otherAlbum.grupo &&
	    		this.duracionTotal == otherAlbum.duracionTotal) {
	    	return Objects.equals(this.getTrackList(), otherAlbum.trackList);
	    } else {
	    	return false;
	    }
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(titulo, autor, grupo, duracionTotal, getTrackList());
	}
	
	public static void main(String[] args) {
		
	}

}
