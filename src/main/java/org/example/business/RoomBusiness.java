package org.example.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.example.entities.Room;
import org.example.repository.RoomRepository;

@Named
public class RoomBusiness implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private RoomRepository roomRepository;
	
	//Insert
	@Transactional
	public Long insert(Room room) throws Exception{
		return roomRepository.insert(room);
	}
	
	//Update
	@Transactional
	public Long update(Room room) throws Exception{
		return roomRepository.update(room);
	}
	
	//Delete
	@Transactional
	public void delete(Room room) throws Exception{
		roomRepository.delete(room);
	}

	//List
	@Transactional
	public List<Room> getAll() throws Exception{
		return roomRepository.findAll();
	}
	
	@Transactional
	public List<Room> getAllByService(Long id) throws Exception{
		return roomRepository.findAllByService(id);
	}
}
