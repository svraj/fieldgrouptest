package com.rnd.tms.data.entity;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rnd.tms.FieldGroupTestApplication;
import com.rnd.tms.data.entity.TimingProfile;
import com.rnd.tms.data.repository.ClientRepository;
import com.rnd.tms.data.repository.TimingProfileRepository;
import com.rnd.tms.data.util.TMSTestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=FieldGroupTestApplication.class)
public class TimingProfileTest {
	
	@Autowired
	private TimingProfileRepository timingProfileRepository;
	@Autowired
	private ClientRepository clientRepository;
	
	@Test
	//@Transactional
	public void testCreateTimingProfile(){
		Client client = new Client("ClientCreatedForDummyTimingProfile");
		client = clientRepository.save(client);
		TimingProfile timingProfile = TMSTestUtil.getDummyTimingProfile(client);
		timingProfileRepository.save(timingProfile);
		assertNotNull(timingProfile.getId());
	}

}
