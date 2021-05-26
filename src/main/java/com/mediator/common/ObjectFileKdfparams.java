package com.mediator.common;





import java.io.Serializable;

public class ObjectFileKdfparams implements Serializable{
	private static final long serialVersionUID = 1L;
	private long dklen;
    private long n;
    private long p;
    private long r;
    private String salt;
    
    
	public long getDklen() {
		return dklen;
	}
	public void setDklen(long dklen) {
		this.dklen = dklen;
	}
	public long getN() {
		return n;
	}
	public void setN(long n) {
		this.n = n;
	}
	public long getP() {
		return p;
	}
	public void setP(long p) {
		this.p = p;
	}
	public long getR() {
		return r;
	}
	public void setR(long r) {
		this.r = r;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
    
    
}
