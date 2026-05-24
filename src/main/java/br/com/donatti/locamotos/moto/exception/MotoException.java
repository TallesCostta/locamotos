package br.com.donatti.locamotos.moto.exception;

public class MotoException extends RuntimeException
{
    public MotoException() {}

    public MotoException(final String message)
    {
        super(message);
    }

    public MotoException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
