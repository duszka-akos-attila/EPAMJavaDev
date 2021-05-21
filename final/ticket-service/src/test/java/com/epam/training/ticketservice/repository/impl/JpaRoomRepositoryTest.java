package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.RoomDao;
import com.epam.training.ticketservice.dataaccess.projection.RoomProjection;
import com.epam.training.ticketservice.domain.Room;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JpaRoomRepositoryTest {

    private JpaRoomRepository jpaRoomRepository;
    private RoomDao roomDao;

    private final Room room = new Room("Test1", 1, 1);
    private final Room room2 = new Room("Test2", 2, 2);
    private final RoomProjection roomProjection = new RoomProjection(
            null, room.getRoomName(), room.getSeatRows(), room.getSeatColumns());
    private final RoomProjection roomProjection2 = new RoomProjection(
            null, room2.getRoomName(), room2.getSeatRows(), room2.getSeatColumns());

    @BeforeEach
    void setUp() {
        roomDao = Mockito.mock(RoomDao.class);
        jpaRoomRepository = new JpaRoomRepository(roomDao);
    }

    @Test
    void testCreateRoomCreatesRoomSuccessfully() throws Exception {
        // Given

        // When
        jpaRoomRepository.createRoom(room);

        // Then
        Mockito.verify(roomDao, Mockito.times(1)).save(roomProjection);
    }

    @Test
    void testCreateRoomThrowsExceptionWhenRoomAlreadyExists() throws Exception {
        // Given
        Mockito.when(roomDao.findByRoomName(room.getRoomName())).thenReturn(Optional.of(roomProjection));
        // When
        Assertions.assertThrows(Exception.class, () -> jpaRoomRepository.createRoom(room));

        // Then
        Mockito.verify(roomDao, Mockito.times(0)).save(roomProjection);
    }

    @Test
    void testGetAllRoomsShouldReturnSuccessfully() {
        // Given
        ArrayList<Room> expectedResult = new ArrayList<Room>(List.of(room, room2));
        Mockito.when(roomDao.findAll()).thenReturn(new ArrayList<RoomProjection>(List.of(roomProjection, roomProjection2)));

        // When
        ArrayList<Room> actualResult = jpaRoomRepository.getAllRooms();

        // Then
        Assertions.assertEquals(expectedResult, actualResult);
        Mockito.verify(roomDao).findAll();
    }

    @Test
    void testFindRoomByRoomNameReturnSuccessfully() throws Exception {
        // Given
        Mockito.when(roomDao.findByRoomName(room.getRoomName())).thenReturn(Optional.of(roomProjection));

        // When
        Assertions.assertEquals(room, jpaRoomRepository.findRoomByRoomName(room.getRoomName()));

        // Then
        Mockito.verify(roomDao).findByRoomName(room.getRoomName());
    }

    @Test
    void testFindRoomByRoomNameThrowsExceptionWhenRoomNotFound() throws Exception {
        // Given
        Mockito.when(roomDao.findByRoomName(room.getRoomName())).thenReturn(Optional.empty());

        // When
        Assertions.assertThrows(Exception.class, () -> jpaRoomRepository.findRoomByRoomName(room.getRoomName()));

        // Then
        Mockito.verify(roomDao).findByRoomName(room.getRoomName());
    }

    @Test
    void testUpdateRoomByRoomUpdatesRoomSuccessfully() throws Exception {
        //Given
        Mockito.when(roomDao.findByRoomName(room.getRoomName())).thenReturn(Optional.of(roomProjection));
        Room updatedRoom = new Room(room.getRoomName(), room2.getSeatRows(), room2.getSeatColumns());
        RoomProjection updatedRoomProjection = new RoomProjection(
                roomProjection.getRoomId(), updatedRoom.getRoomName(), updatedRoom.getSeatRows(), updatedRoom.getSeatColumns());

        // When
        jpaRoomRepository.updateRoomByRoom(updatedRoom);

        // Then
        Mockito.verify(roomDao).save(updatedRoomProjection);

    }

    @Test
    void testUpdateRoomByRoomThrowsExceptionWhenRoomNotFound() throws Exception {
        //Given
        Mockito.when(roomDao.findByRoomName(room.getRoomName())).thenReturn(Optional.empty());
        Room updatedRoom = new Room(room.getRoomName(), room2.getSeatRows(), room2.getSeatColumns());
        RoomProjection updatedRoomProjection = new RoomProjection(
                roomProjection.getRoomId(), updatedRoom.getRoomName(), updatedRoom.getSeatRows(), updatedRoom.getSeatColumns());

        // When
        Assertions.assertThrows(Exception.class, () -> jpaRoomRepository.updateRoomByRoom(updatedRoom));

        // Then
        Mockito.verify(roomDao, Mockito.times(0)).save(updatedRoomProjection);

    }

    @Test
    void testDeleteRoomByRoomNameDeletesRoomSuccessfully() throws Exception {
        // Given
        Mockito.when(roomDao.findByRoomName(room.getRoomName())).thenReturn(Optional.of(roomProjection));

        // When
        jpaRoomRepository.deleteRoomByRoomName(room.getRoomName());

        // Then
        Mockito.verify(roomDao, Mockito.times(1)).deleteByRoomName(room.getRoomName());
    }

    @Test
    void testDeleteRoomByRoomNameThrowsExceptionWhenRoomNotFound() {
        // Given
        Mockito.when(roomDao.findByRoomName(room.getRoomName())).thenReturn(Optional.empty());
        // When
        Assertions.assertThrows(Exception.class, () -> jpaRoomRepository.deleteRoomByRoomName(room.getRoomName()));

        // Then
        Mockito.verify(roomDao, Mockito.times(0)).deleteByRoomName(room.getRoomName());
    }
}