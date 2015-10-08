package org.eu.mmacedo.integration.winnpipes.inbound;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

public class WinNamedPipesInboundEndpoint extends MessageProducerSupport {
	String pipename;
	RandomAccessFile pipe;
	volatile boolean running = false;

	@Override
	protected void onInit() {
		System.out.println("pipename: " + pipename);
		recover();
		super.onInit();
	}

	private void recover() {
		while (pipe == null) {
			try {
				pipe = new RandomAccessFile(pipename, "rw");
			} catch (FileNotFoundException e) {
				if (pipe != null) {
					try {
						pipe.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					pipe = null;
				}
				//e.printStackTrace();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void doStart() {
		running = true;
		if (pipe != null) {
			while (running) {
				try {
					String pl = pipe.readLine();
					if (pl != null && !pl.isEmpty()) {
						Message<String> msg = MessageBuilder.withPayload(pl).build();
						sendMessage(msg);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		super.doStart();
	}

	@Override
	protected void doStop() {
		running = false;
		try {
			pipe.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.doStop();
	}

	public String getPipename() {
		return pipename;
	}

	public void setPipename(String pipename) {
		this.pipename = pipename;
	}

}
