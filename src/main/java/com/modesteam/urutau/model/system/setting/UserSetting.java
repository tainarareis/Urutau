package com.modesteam.urutau.model.system.setting;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.modesteam.urutau.model.User;

/**
 * TODO when migrate to postgres, put:
 * 
 *	@SequenceGenerator(name="normal_setting_gen", sequenceName="normal_seq", initialValue=100)
 *
 * 	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="normal_setting_gen")
 *
 */
@Entity
public class UserSetting extends Setting {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Transient
	private UserSettingContext context;

	/**
	 * @deprecated only eye JPA
	 */
	public UserSetting() {

	}

	public UserSetting(UserSettingContext context) {
		this.context = context;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void setValue(Object genericValue) {
		switch (context) {
		case THEME:

			break;
		default:
			setValue(String.valueOf(genericValue));
			break;
		}
	}

	@Override
	public Enum<?> getContext() {
		return this.context;
	}
}
