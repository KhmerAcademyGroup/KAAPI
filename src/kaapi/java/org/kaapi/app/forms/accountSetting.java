package org.kaapi.app.forms;

import java.sql.Date;

public class accountSetting {
		private String code;
		private String email;
		private boolean status;
		private Date datetime;
		private String type;
		
		
	
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public boolean isStatus() {
			return status;
		}
		public void setStatus(boolean status) {
			this.status = status;
		}
		public Date getDatetime() {
			return datetime;
		}
		public void setDatetime(Date datetime) {
			this.datetime = datetime;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
}
