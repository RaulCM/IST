package urjc.ist.playlist;

import java.util.ArrayList;
import java.util.Objects;
import urjc.ist.playlist.Cancion.Codecs;

public class Playlist {

	private String nombre;
	private int numAlbumes;
	private int numCanciones;
	private int duracionTotal;
	private ArrayList<Album> albumList;
	
	public Playlist() {
		nombre = "";
		numAlbumes = 0;
		numCanciones = 0;
		duracionTotal = 0;
	}
	
	public Playlist(String nombre) {
		this.nombre = nombre;
		numAlbumes = 0;
		numCanciones = 0;
		duracionTotal = 0;
		albumList = new ArrayList<Album>();
	}

	public String getNombre(){
		return nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public int getNumAlbumes(){
		return numAlbumes;
	}
	
	public void setNumAlbumes(int numAlbumes){
		this.numAlbumes = numAlbumes;
	}
	
	public int getNumCanciones(){
		return numCanciones;
	}
	
	public void setNumCanciones(int numCanciones){
		this.numCanciones = numCanciones;
	}
	
	public int getDuracionTotal() {
		return duracionTotal;
	}
	
	public void setDuracionTotal(int duracionTotal) {
		this.duracionTotal = duracionTotal;
	}
	
	public void addAlbum(Album unAlbum){
		albumList.add(unAlbum);
		numAlbumes = numAlbumes + 1;
		numCanciones = numCanciones + unAlbum.getTrackList().size();
		duracionTotal = duracionTotal + unAlbum.getDuracionTotal();
	}
	
	public void addAlbum(int posicion, Album unAlbum){
		albumList.add(posicion, unAlbum);
		numAlbumes = numAlbumes + 1;
		numCanciones = numCanciones + unAlbum.getTrackList().size();
		setDuracionTotal(duracionTotal + unAlbum.getDuracionTotal());
	}
	
	public void removeAlbum(){
		try{
			duracionTotal = duracionTotal - albumList.get(albumList.size() - 1).getDuracionTotal();
			numCanciones = numCanciones - albumList.get(albumList.size() - 1).getTrackList().size();
			albumList.remove(albumList.size() - 1);
			numAlbumes = numAlbumes - 1;
		}
		catch(IndexOutOfBoundsException exception){
			System.out.println("Capturada la excepción:" + exception);
		}
	}
	
	public void removeAlbum(int posicion){
		try{
			duracionTotal = duracionTotal - albumList.get(posicion).getDuracionTotal();
			numCanciones = numCanciones - albumList.get(posicion).getTrackList().size();
			albumList.remove(posicion);
			numAlbumes = numAlbumes - 1;
		}
		catch(IndexOutOfBoundsException exception){
			System.out.println("Capturada la excepción:" + exception);
		}
	}
	
	public void clearList(){
		albumList.clear();
		numAlbumes = 0;
	}
	
	public ArrayList<Album> getAlbumList() {
		/**
		 * Método que devuelve la lista de álbumes actualmente
		 * incluídos en la playlist
		 */
		return albumList;
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
				"Nombre de la playlist: " + nombre,
				"Número de álbumes: " + numAlbumes,
				"Número de canciones: " + numCanciones,
				"Duración: " + timeString,
				"----------",
				"Lista de álbumes:\n" + albumList + "\n");
	}

	@Override
	public boolean equals(Object other) {
		/**
		 * Implementación de un método de comparación del contenido
		 * de dos playlists
		 */
		if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Playlist)) return false;
	    Playlist otherPlaylist = (Playlist)other;
	    if (this.nombre == otherPlaylist.nombre &&
	    		this.numAlbumes == otherPlaylist.numAlbumes &&
	    		this.numCanciones == otherPlaylist.numCanciones &&
	    		this.duracionTotal == otherPlaylist.duracionTotal) {
	    	return Objects.equals(this.getAlbumList(), otherPlaylist.albumList);
	    } else {
	    	return false;
	    }
	}

	@Override
	public int hashCode(){
		return Objects.hash(nombre, numAlbumes, numCanciones, duracionTotal, getAlbumList());
	}
	
	public static void main(String[] args) {

	}
}
