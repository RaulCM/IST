package urjc.ist.playlist;

import java.util.ArrayList;
import java.util.Scanner;

import urjc.ist.playlist.Cancion.Codecs;

import java.util.Iterator;
import java.util.List;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GestionPlaylist {

	public static boolean listaExiste(String nombrePlaylist, List<Playlist> playlistList){
		boolean encontrada = false;
		Iterator<Playlist> itPlay = playlistList.iterator();
		while(itPlay.hasNext()){
			Playlist playAux = itPlay.next();
			if(playAux.getNombre().equals(nombrePlaylist)){
				encontrada = true;
			}
		}
		return encontrada;
	}
	
	public static void crearPlaylist(String nombrePlaylist, List<Playlist> playlistList){
		if(listaExiste(nombrePlaylist, playlistList)){
			System.out.println("Ya existe una lista con ese nombre.");
		}else{
			Playlist playlist = new Playlist(nombrePlaylist);;
			playlistList.add(playlist);
		}
	}
	
	public static void borrarPlaylist(String nombrePlaylist, List<Playlist> playlistList){
		if(listaExiste(nombrePlaylist, playlistList)){
			Iterator<Playlist> itPlay = playlistList.iterator();
			while(itPlay.hasNext()){
				if(itPlay.next().getNombre().equals(nombrePlaylist)){
					itPlay.remove();
					System.out.println("Lista borrada con éxito.");
				}
			}
		}else{
			System.out.println("Esa lista no existe.");
		}
	}
	
	public static void modificarPlaylist(String nombrePlaylist, List<Playlist> playlistList){
		if(listaExiste(nombrePlaylist, playlistList)){
			System.out.println("Introduzca el nuevo nombre de la playlist:");
			Scanner sc = new Scanner(System.in);
			String nombreNuevo = sc.nextLine();
			if(listaExiste(nombreNuevo, playlistList)){
				System.out.println("Ya existe una playlist con ese nombre");
			}else{
				Iterator<Playlist> itPlay = playlistList.iterator();
				while(itPlay.hasNext()){
					Playlist playAux = itPlay.next();
					if(playAux.getNombre().equals(nombrePlaylist)){
						playAux.setNombre(nombreNuevo);
					}
				}
			}
		}else{
			System.out.println("Esa lista no existe.");
		}
	}
	
	public static boolean albumExiste(String nombrePlaylist, Album album, List<Playlist> playlistList){
		boolean encontrado = false;
		if(listaExiste(nombrePlaylist, playlistList)){
			Iterator<Playlist> itPlay = playlistList.iterator();
			while(itPlay.hasNext()){
				Playlist playAux = itPlay.next();
				if(playAux.getNombre().equals(nombrePlaylist)){
					Iterator<Album> itAlbum = playAux.getAlbumList().iterator();
					while(itAlbum.hasNext()){
						Album albumAux = itAlbum.next();
						if(albumAux.getTitulo().equals(album.getTitulo()) && albumAux.getAutor().equals(album.getAutor()) && albumAux.getGrupo().equals(album.getGrupo())){
							encontrado = true;
						}
					}
				}
			}
		}else{
			System.out.println("Esa lista no existe.");
		}
		return encontrado;
	}
	
	public static void crearAlbum(String nombrePlaylist, Album album, List<Playlist> playlistList){
		if(albumExiste(nombrePlaylist, album, playlistList)){
			System.out.println("Ya existe un álbum con ese nombre.");
		}else{
			Iterator<Playlist> itPlay = playlistList.iterator();
			while(itPlay.hasNext()){
				Playlist playAux = itPlay.next();
				if(playAux.getNombre().equals(nombrePlaylist)){
					playAux.addAlbum(album);
				}
			}
		}
	}
	
	public static void borrarAlbum(String nombrePlaylist, Album album, List<Playlist> playlistList){
		if(albumExiste(nombrePlaylist, album, playlistList)){
			Iterator<Playlist> itPlay = playlistList.iterator();
			while(itPlay.hasNext()){
				Playlist playAux = itPlay.next();
				if(playAux.getNombre().equals(nombrePlaylist)){
					Iterator<Album> itAlbum = playAux.getAlbumList().iterator();
					while(itAlbum.hasNext()){
						Album albumAux = itAlbum.next();
						if(albumAux.getTitulo().equals(album.getTitulo()) && albumAux.getAutor().equals(album.getAutor()) && albumAux.getGrupo().equals(album.getGrupo())){
							itAlbum.remove();
							playAux.setNumAlbumes(playAux.getNumAlbumes()-1);
							playAux.setNumCanciones(playAux.getNumCanciones()-albumAux.getTrackList().size());
							playAux.setDuracionTotal(playAux.getDuracionTotal()-albumAux.getDuracionTotal());
							System.out.println("Álbum borrado con éxito.");
						}
					}
				}
			}
		}else{
			System.out.println("Ese álbum no existe.");
		}
	}
	
	public static void modificarAlbum(String nombrePlaylist, Album album, List<Playlist> playlistList){
		if(albumExiste(nombrePlaylist, album, playlistList)){
			System.out.println("Introduzca los nuevos datos del álbum: ");
			Album albumNuevo = datosAlbum();
			if(albumExiste(nombrePlaylist, albumNuevo, playlistList)){
				System.out.println("Ya existe ese álbum");
			}else{
				Iterator<Playlist> itPlay = playlistList.iterator();
				while(itPlay.hasNext()){
					Playlist playAux = itPlay.next();
					if(playAux.getNombre().equals(nombrePlaylist)){
						Iterator<Album> itAlbum = playAux.getAlbumList().iterator();
						while(itAlbum.hasNext()){
							Album albumAux = itAlbum.next();
							if(albumAux.getTitulo().equals(album.getTitulo()) && albumAux.getAutor().equals(album.getAutor()) && albumAux.getGrupo().equals(album.getGrupo())){
								albumAux.setTitulo(albumNuevo.getTitulo());
								albumAux.setAutor(albumNuevo.getAutor());
								albumAux.setGrupo(albumNuevo.getGrupo());
							}
						}
					}
				}
			}
		}else{
			System.out.println("Ese álbum no existe.");
		}
	}
	
	public static Album datosAlbum(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Título: ");
		String titulo = sc.nextLine();
		System.out.println("Autor: ");
		String autor = sc.nextLine();
		System.out.println("Grupo: ");
		String grupo = sc.nextLine();
		Album album = new Album(titulo, autor, grupo);
		return album;
	}
	
	public static Cancion datosCancion(){
		int opcionCodec = 0;
		Codecs formato = null;
		Scanner sc = new Scanner(System.in);
		System.out.println("Título: ");
		String titulo = sc.nextLine();
		System.out.println("Autor: ");
		String autor = sc.nextLine();
		System.out.println("Duración: ");
		int duracion = sc.nextInt();
		sc.nextLine();
		while(opcionCodec < 1 || opcionCodec > 3){
			System.out.println("Formato (introduza el número correspondiente): ");
			System.out.println("1-MP3");
			System.out.println("2-FLAC");
			System.out.println("3-OGG");
			opcionCodec = sc.nextInt();
			sc.nextLine();
			switch(opcionCodec){
				case 1:
					formato = Codecs.MP3;
					break;
				case 2:
					formato = Codecs.FLAC;
					break;
				case 3:
					formato = Codecs.OGG;
					break;
				default:
					formato = null;
			}
		}
		Cancion cancion = new Cancion(titulo, autor, duracion, formato);
		return cancion;
	}
	
	public static boolean cancionExiste(String nombrePlaylist, Album album, Cancion cancion, List<Playlist> playlistList){
		boolean encontrado = false;
		Iterator<Playlist> itPlay = playlistList.iterator();
		while(itPlay.hasNext()){
			Playlist playAux = itPlay.next();
			if(playAux.getNombre().equals(nombrePlaylist)){
				Iterator<Album> itAlbum = playAux.getAlbumList().iterator();
				while(itAlbum.hasNext()){
					Album albumAux = itAlbum.next();
					if(albumAux.getTitulo().equals(album.getTitulo()) && albumAux.getAutor().equals(album.getAutor()) && albumAux.getGrupo().equals(album.getGrupo())){
						Iterator<Cancion> itCancion = albumAux.getTrackList().iterator();
						while(itCancion.hasNext()){
							Cancion cancionAux = itCancion.next();
							if(cancionAux.getTitulo().equals(cancion.getTitulo()) && cancionAux.getAutor().equals(cancion.getAutor()) && cancionAux.getDuracion() == cancion.getDuracion() && cancionAux.getFormato().equals(cancion.getFormato())){
								encontrado = true;
							}
						}
					}
				}
			}
		}
		return encontrado;
	}
	
	public static void crearCancion(String nombrePlaylist, Album album, Cancion cancion, List<Playlist> playlistList){
		if(cancionExiste(nombrePlaylist, album, cancion, playlistList)){
			System.out.println("Esa canción ya existe");
		}else{
			Iterator<Playlist> itPlay = playlistList.iterator();
			while(itPlay.hasNext()){
				Playlist playAux = itPlay.next();
				if(playAux.getNombre().equals(nombrePlaylist)){
					Iterator<Album> itAlbum = playAux.getAlbumList().iterator();
					while(itAlbum.hasNext()){
						Album albumAux = itAlbum.next();
						if(albumAux.getTitulo().equals(album.getTitulo()) && albumAux.getAutor().equals(album.getAutor()) && albumAux.getGrupo().equals(album.getGrupo())){
							albumAux.addTrack(cancion);
							playAux.setDuracionTotal(playAux.getDuracionTotal()+cancion.getDuracion());
							playAux.setNumCanciones(playAux.getNumCanciones()+1);
						}
					}
				}
			}
		}
	}
	
	public static void borrarCancion(String nombrePlaylist, Album album, Cancion cancion, List<Playlist> playlistList){
		if(cancionExiste(nombrePlaylist, album, cancion, playlistList)){
			Iterator<Playlist> itPlay = playlistList.iterator();
			while(itPlay.hasNext()){
				Playlist playAux = itPlay.next();
				if(playAux.getNombre().equals(nombrePlaylist)){
					Iterator<Album> itAlbum = playAux.getAlbumList().iterator();
					while(itAlbum.hasNext()){
						Album albumAux = itAlbum.next();
						if(albumAux.getTitulo().equals(album.getTitulo()) && albumAux.getAutor().equals(album.getAutor()) && albumAux.getGrupo().equals(album.getGrupo())){
							Iterator<Cancion> itCancion = albumAux.getTrackList().iterator();
							while(itCancion.hasNext()){
								Cancion cancionAux = itCancion.next();
								if(cancionAux.getTitulo().equals(cancion.getTitulo()) && cancionAux.getAutor().equals(cancion.getAutor()) && cancionAux.getDuracion() == cancion.getDuracion() && cancionAux.getFormato().equals(cancion.getFormato())){
									itCancion.remove();
									albumAux.setDuracionTotal(albumAux.getDuracionTotal()-cancion.getDuracion());
									playAux.setDuracionTotal(playAux.getDuracionTotal()-cancion.getDuracion());
									playAux.setNumCanciones(playAux.getNumCanciones()-1);
								}
							}
						}
					}
				}
			}
		}else{
			System.out.println("Esa canción no existe");
		}
	}
	
	public static void modificarCancion(String nombrePlaylist, Album album, Cancion cancion, List<Playlist> playlistList){
		if(cancionExiste(nombrePlaylist, album, cancion, playlistList)){
			System.out.println("Introduzca los nuevos datos de la canción: ");
			Cancion cancionNueva = datosCancion();
			if(cancionExiste(nombrePlaylist, album, cancionNueva, playlistList)){
				System.out.println("Ya existe esa canción");
			}else{
				Iterator<Playlist> itPlay = playlistList.iterator();
				while(itPlay.hasNext()){
					Playlist playAux = itPlay.next();
					if(playAux.getNombre().equals(nombrePlaylist)){
						Iterator<Album> itAlbum = playAux.getAlbumList().iterator();
						while(itAlbum.hasNext()){
							Album albumAux = itAlbum.next();
							if(albumAux.getTitulo().equals(album.getTitulo()) && albumAux.getAutor().equals(album.getAutor()) && albumAux.getGrupo().equals(album.getGrupo())){
								Iterator<Cancion> itCancion = albumAux.getTrackList().iterator();
								while(itCancion.hasNext()){
									Cancion cancionAux = itCancion.next();
									if(cancionAux.getTitulo().equals(cancion.getTitulo()) && cancionAux.getAutor().equals(cancion.getAutor()) && cancionAux.getDuracion() == cancion.getDuracion() && cancionAux.getFormato().equals(cancion.getFormato())){
										cancionAux.setTitulo(cancionNueva.getTitulo());
										cancionAux.setAutor(cancionNueva.getAutor());
										cancionAux.setDuracion(cancionNueva.getDuracion());
										cancionAux.setFormato(cancionNueva.getFormato());
										albumAux.setDuracionTotal(albumAux.getDuracionTotal()-cancion.getDuracion());
										albumAux.setDuracionTotal(albumAux.getDuracionTotal()+cancionNueva.getDuracion());
										playAux.setDuracionTotal(playAux.getDuracionTotal()-cancion.getDuracion());
										playAux.setDuracionTotal(playAux.getDuracionTotal()+cancionNueva.getDuracion());
									}
								}
							}
						}
					}
				}
			}
		}else{
			System.out.println("Esa canción no existe");
		}
	}
	
	public static List<Playlist> leerDirectorios(){
		List<Playlist> playlistList = new ArrayList<Playlist>();
		String dirPath = "/tmp/GestionPlaylist";
		Path ruta = Paths.get("/", "tmp", "GestionPlaylist");
		if(Files.exists(ruta)){
			File dir = new File(dirPath);
			String[] files = dir.list();
			if (files.length != 0) {
			    for (String aFile : files) {
			        crearPlaylist(aFile, playlistList);
			        String dirPath2 = dirPath + "/" + aFile;
			        File dir2 = new File(dirPath2);
			        String[] files2 = dir2.list();
			        if (files2.length != 0) {
			            for (String aFile2 : files2) {
			                String[] parts = aFile2.split(",");
			                String titulo = parts[0];
			                String autor = parts[1];
			                String grupo = parts[2];
			                Album album = new Album(titulo, autor, grupo);
			                crearAlbum(aFile, album, playlistList);
			                String dirPath3 = dirPath2 + "/" + aFile2;
			                File dir3 = new File(dirPath3);
			                String[] files3 = dir3.list();
			                if (files3.length != 0) {
			                    for (String aFile3 : files3) {
			                        String[] partsC = aFile3.split(",");
					                String tituloC = partsC[0];
					                String autorC = partsC[1];
					                Integer duracion = Integer.parseInt(partsC[2]);
					                String formatoString = partsC[3];
					                Codecs formato;
									switch(formatoString){
									case "MP3":
										formato = Codecs.MP3;
										break;
									case "FLAC":
										formato = Codecs.FLAC;
										break;
									case "OGG":
										formato = Codecs.OGG;
										break;
									default:
										formato = null;
									}
					                Cancion cancion = new Cancion(tituloC, autorC, duracion, formato);
					                crearCancion(aFile, album, cancion, playlistList);
			                    }
			                }   
			            }
			        }
			    }
			}
		}
		return playlistList;
	}
	
	
	public static void borrarDirectorio(File folder) {
	    File[] files = folder.listFiles();
	    if(files!=null) { //some JVMs return null for empty dirs
	        for(File f: files) {
	            if(f.isDirectory()) {
	            	borrarDirectorio(f);
	            } else {
	                f.delete();
	            }
	        }
	    }
	    folder.delete();
	}
	
	
	public static void crearDirectorios(List<Playlist> playlistList){
		Path ruta = Paths.get("/", "tmp", "GestionPlaylist");
		String carpeta = "/tmp/GestionPlaylist";
		try {
			File file = new File(carpeta);
			borrarDirectorio(file);
			Files.createDirectories(ruta);
		} catch (IOException e) {
			System.err.println(e);
		}
		Iterator<Playlist> it = playlistList.iterator();
		while(it.hasNext()){
			Playlist Play = it.next();
			Path rutaLista = Paths.get("/", "tmp", "GestionPlaylist", Play.getNombre());
			try {
				Files.createDirectories(rutaLista);
				Iterator<Album> itA = Play.getAlbumList().iterator();
				while(itA.hasNext()){
					Album albumAux = itA.next();
					String directorioAlbum = albumAux.getTitulo()+ "," + albumAux.getAutor() + "," + albumAux.getGrupo();
					Path rutaAlbum = Paths.get("/", "tmp", "GestionPlaylist", Play.getNombre(), directorioAlbum);
					Files.createDirectories(rutaAlbum);
					Iterator<Cancion> itC = albumAux.getTrackList().iterator();
					while(itC.hasNext()){
						Cancion cancionAux = itC.next();
						Codecs formato = cancionAux.getFormato();
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
						String directorioCancion = cancionAux.getTitulo() + "," + cancionAux.getAutor() + "," + cancionAux.getDuracion() + "," + formatoString;
						Path rutaCancion = Paths.get("/", "tmp", "GestionPlaylist", Play.getNombre(), directorioAlbum, directorioCancion);
						Files.createFile(rutaCancion);
					}
				}
			} catch (IOException e) {
				System.err.println(e);
			}			
		}
	}
	
	public static void main(String[] args) {
		List<Playlist> playlistList = new ArrayList<Playlist>();
		Iterator<Playlist> it;
		Scanner sc = new Scanner(System.in);
		int opcion = 0;
		String nombre, nombrePlaylist;
		boolean encontrado = false;
		boolean vacio = false;
		Album album = null;
		Cancion track = null;
		
		playlistList = leerDirectorios();
		while (opcion !=5){
			System.out.println("\nMenú. Escoja una opción:");
			System.out.println("1-Canciones");
			System.out.println("2-Álbumes");
			System.out.println("3-Playlists");
			System.out.println("4-Imprimir");
			System.out.println("5-Salir\n");
			opcion = sc.nextInt();
			sc.nextLine();
			switch(opcion){
				case 1:
					if (playlistList.isEmpty()){
						System.out.println("Antes de crear un álbum debe crear una playlist.");
					}else{
						System.out.println("Introduzca el nombre de la playlist sobre la que desea trabajar.");
						nombrePlaylist = sc.nextLine();
						it = playlistList.iterator();
						encontrado = false;
						while(it.hasNext()){
							Playlist Play = it.next();
							if(Play.getNombre().equals(nombrePlaylist)){
								vacio = Play.getAlbumList().isEmpty();
								encontrado = true;
							}
						}
						if (encontrado == false){
							System.out.println("Esa lista no existe");
						}
						else if (vacio == true){
							System.out.println("Antes de crear una canción debe crear un álbum.");
						}else{
							System.out.println("Introduzca el nombre del álbum sobre el que desea trabajar.");
							album = datosAlbum();
							if (albumExiste(nombrePlaylist, album, playlistList) == false){
								System.out.println("Ese álbum no existe");
							}else{
								System.out.println("Menú Canciones. Escoja una opción:");
								System.out.println("1-Crear Canción");
								System.out.println("2-Borrar Canción");
								System.out.println("3-Modificar Canción");
								opcion = sc.nextInt();
								sc.nextLine();
								switch(opcion){
									case 1:
										System.out.println("Introduzca los datos de la canción que desea crear: ");
										track = datosCancion();
										crearCancion(nombrePlaylist, album, track, playlistList);
										break;
									case 2:
										System.out.println("Introduzca los datos de la canción que desea borrar: ");
										track = datosCancion();
										borrarCancion(nombrePlaylist, album, track, playlistList);
										break;
									case 3:
										System.out.println("Introduzca los datos de la canción que desea modificar: ");
										track = datosCancion();
										modificarCancion(nombrePlaylist, album, track, playlistList);
										break;
								}
							}	
						}
					}
					break;
				case 2:
					if (playlistList.isEmpty()){
						System.out.println("Antes de crear un álbum debe crear una playlist.");
					}else{
						System.out.println("Introduzca el nombre de la playlist sobre la que desea trabajar");
						nombre = sc.nextLine();
						if(listaExiste(nombre, playlistList)){
							System.out.println("Menú Álbumes. Escoja una opción:");
							System.out.println("1-Crear Álbum");
							System.out.println("2-Borrar Álbum");
							System.out.println("3-Modificar Álbum");
							opcion = sc.nextInt();
							sc.nextLine();
							switch(opcion){
								case 1:
									System.out.println("Introduzca los datos del álbum que desea crear: ");
									album = datosAlbum();
									crearAlbum(nombre, album, playlistList);
									break;
								case 2:
									System.out.println("Introduzca los datos del álbum que desea borrar: ");
									album = datosAlbum();
									borrarAlbum(nombre, album, playlistList);
									break;
								case 3:
									System.out.println("Introduzca los datos del álbum que desea modificar: ");
									album = datosAlbum();
									modificarAlbum(nombre, album, playlistList);
									break;
								default:
									System.out.println("Escoja una de las opciones mostradas (1-3).");
									break;
							}
						}else{
							System.out.println("Esa lista no existe");
						}
					}
					break;
				case 3:
					System.out.println("Menú Playlists. Escoja una opción:");
					System.out.println("1-Crear Playlist");
					System.out.println("2-Borrar Playlist");
					System.out.println("3-Modificar Playlist");
					opcion = sc.nextInt();
					sc.nextLine();
					switch(opcion){
						case 1:
							System.out.println("Introduzca el nombre de la playlist que desea crear:");
							nombre = sc.nextLine();
							crearPlaylist(nombre, playlistList);
							break;
						case 2:
							System.out.println("Introduzca el nombre de la playlist que desea borrar:");
							nombre = sc.nextLine();
							borrarPlaylist(nombre, playlistList);
							break;
						case 3:
							System.out.println("Introduzca el nombre de la playlist que desea modificar:");
							nombre = sc.nextLine();
							modificarPlaylist(nombre, playlistList);
							break;
						default:
							System.out.println("Escoja una de las opciones mostradas (1-3).");
							break;
					}
					break;
				case 4:
					System.out.println(playlistList);
					break;
				case 5:
					System.out.println("Saliendo");
					crearDirectorios(playlistList);
					break;
				default:
					System.out.println("Escoja una de las opciones mostradas (1-5).");
					break;
			}
		}
	sc.close();
	}
}
