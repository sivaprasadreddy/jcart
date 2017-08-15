package com.sivalabs.jcart;

/**
 * @author Siva
 *
 */
public class JCartException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public JCartException()
	{
		super();
	}

	public JCartException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public JCartException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public JCartException(String message)
	{
		super(message);
	}

	public JCartException(Throwable cause)
	{
		super(cause);
	}
	
}
