    private void getFile(String filename) {
        // TODO: 14.06.2021
        try {
            out.writeUTF("download");
            out.writeUTF(filename);

            File file = new File("client" + File.separator + filename);

            if (!file.exists()){
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file);
            long size = in.readLong();
            byte[] buffer = new byte[8 * 1024];

            for (int i = 0; i < (size + (buffer.length-1))/buffer.length; i++) {
                int read = in.read(buffer);
                fos.write(buffer, 0, read);
            }

            fos.close();

            out.writeUTF("OK");
        } catch (IOException e) {
            try {
                out.writeUTF("WRONG");
            }catch (IOException ex){
                ex.printStackTrace();
            }
            System.err.println("Downloading error");
            e.printStackTrace();
        }

    }
