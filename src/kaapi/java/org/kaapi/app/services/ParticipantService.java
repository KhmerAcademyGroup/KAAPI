package org.kaapi.app.services;

import java.util.List;

import org.kaapi.app.forms.FrmParticipants;

public interface ParticipantService {
	
	public List<FrmParticipants> listAll();
	public boolean add(FrmParticipants part);

}
