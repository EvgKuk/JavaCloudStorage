    @Override
    public void run() {
        try (
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream())
        ) {
            System.out.printf("Client %s connected\n", socket.getInetAddress());
            while (true) {
                String command = in.readUTF();
                if ("upload".equals(command)) {
                   uploading(in, out);
                }

                if ("download".equals(command)) {
                    // TODO: 14.06.2021
                    downloading(out, in);
                   
                }
                if ("exit".equals(command)) {
                    System.out.printf("Client %s disconnected correctly\n", socket.getInetAddress());
                    break;
                }

                System.out.println(command);
//                out.writeUTF(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downloading(DataOutputStream out, DataInputStream in) throws IOException {
        String filename = in.readUTF();
        try {
            File file = new File("server" + File.separator + filename);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }

            long fileLength = file.length();

            FileInputStream fis = new FileInputStream(file);

            out.writeLong(fileLength);

            int read;
            byte[] buffer = new byte[8 * 1024];
            while ((read = fis.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

            out.flush();

            String status = in.readUTF();
            System.out.println("Downloading status: " + status);
        } catch (FileNotFoundException e){
            System.err.println("File not found: " + filename);
        }
    }
