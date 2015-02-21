package com.bignerdranch.android.criminalintent;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;

public class CriminalIntentJSONSerializer {

	private Context mContext;
	private String mFilename;

	public CriminalIntentJSONSerializer(Context context, String filname) {
		this.mContext = context;
		this.mFilename = filname;
	}

	public void saveCrimes(ArrayList<Crime> crimes) throws JSONException,
			IOException {
		// Build an array in JSON
		JSONArray array = new JSONArray();
		for (Crime crime : crimes) {
			array.put(crime.toJSON());
		}

		// Write the file to disk
		Writer writer = null;
		try {
			OutputStream out = mContext.openFileOutput(mFilename,
					Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
}
