package org.kaapi.app.forms;

import java.sql.Date;

public class FrmUserResetPassword {
		private String resetId;
		private String resetEmail;
		private boolean resetStatus;
		private Date resetDate;
		
		public String getResetId() {
			return resetId;
		}
		public void setResetId(String resetId) {
			this.resetId = resetId;
		}
		public String getResetEmail() {
			return resetEmail;
		}
		public void setResetEmail(String resetEmail) {
			this.resetEmail = resetEmail;
		}
		public boolean isResetStatus() {
			return resetStatus;
		}
		public void setResetStatus(boolean resetStatus) {
			this.resetStatus = resetStatus;
		}
		public Date getResetDate() {
			return resetDate;
		}
		public void setResetDate(Date resetDate) {
			this.resetDate = resetDate;
		}
}
