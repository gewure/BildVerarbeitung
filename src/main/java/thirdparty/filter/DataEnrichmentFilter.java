package thirdparty.filter;

import java.io.StreamCorruptedException;
import java.security.InvalidParameterException;

import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

public abstract class DataEnrichmentFilter<in, out>  extends AbstractFilter<in, out> implements Runnable {

	private out m_TempWriteEntity = null;
    private boolean m_EndOfStream = false;


    public DataEnrichmentFilter(Readable<in> input, Writable<out> output) throws InvalidParameterException {
        super(input, output);
    }

    public DataEnrichmentFilter(Readable<in> input) throws InvalidParameterException {
        super(input);
    }

    public DataEnrichmentFilter(Writable<out> output) throws InvalidParameterException {
        super(output);
    }

    /**
     * read an indsys.entity from the thirdparty.indsys.filter. the thirdparty.indsys.filter will act like an passive-thirdparty.indsys.filter
     */
    public out read() throws StreamCorruptedException{
        // just read the next indsys.entity and return it
        return getNextEntity();
    }

    /**
     * write an indsys.entity into the thirdparty.indsys.filter. the thirdparty.indsys.filter will act like an passive-thirdparty.indsys.filter
     * and passes the indsys.entity to the next thirdparty.indsys.filter, after it processed it
     * @param value
     * @throws StreamCorruptedException
     */
    public void write(in value) throws StreamCorruptedException {
        if (!m_EndOfStream) {
            if (value != ENDING_SIGNAL) {
                if (m_TempWriteEntity == null ) m_TempWriteEntity = getNewEntityObject();
                if (fillEntity(value, m_TempWriteEntity)) {
                    writeOutput(m_TempWriteEntity);
                    m_TempWriteEntity = null;
                }
            }else {
                if (m_TempWriteEntity != null) writeOutput(m_TempWriteEntity);
                sendEndSignal();
            }
        }
        
    }
    
    /**
     * reads the whole next indsys.entity from input
     * @return the next indsys.entity
     * @throws StreamCorruptedException
     */
    protected out getNextEntity() throws StreamCorruptedException {
        if (!m_EndOfStream) {
            out entity = getNewEntityObject();
            boolean finished = false;
            in input;
            do{
                input = readInput();
                if (input != null) {
                    finished = fillEntity(input, entity); 
                }else {
                    m_EndOfStream = true;
                    finished = true;
                }
            }while(!finished);
            return entity;
        }else {
            return null;
        }
    }
    
    /**
     * runs the thirdparty.indsys.filter in active-mode
     */
    public void run() {
        out output = null;//getNewEntityObject();
        in input = null;
        try {
            do {

                output = read();

                if (output != null) {
                    writeOutput(output);
                }
            }while(output != null);
            sendEndSignal();
        } catch (StreamCorruptedException e) {
            System.out.print("Thread reports error: ");
            System.out.println(Thread.currentThread().getId() + " (" + Thread.currentThread().getName() + ")");
            e.printStackTrace();
        }
    }
    
    /**
     * fill the indsys.entity with the next given value
     * @param nextVal
     * @param entity
     * @return true if the indsys.entity is finished
     */
    protected abstract boolean fillEntity(in nextVal, out entity);
    /**
     * Abstract Filter class asks implementation for new Entity-Object
     * (because it doesn't know the constructor-arguemnts)
     * @return
     */
    protected abstract out getNewEntityObject();
}
