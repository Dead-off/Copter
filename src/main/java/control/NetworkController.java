package control;

import facade.Copter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import proto.CopterDirection;

import java.util.ArrayList;
import java.util.List;

public class NetworkController implements UserController {

    private Copter copter;
    private final int port;

    private volatile ServerCallback onServerStart;
    private volatile ServerCallback onChannelRead;

    public NetworkController(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ByteArrayEncoder());
                            ch.pipeline().addLast(new ByteArrayDecoder());
                            ch.pipeline().addLast(new Handler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            ServerCallback onServerStartScope = this.onServerStart;
            if (onServerStartScope != null) {
                onServerStartScope.handle();
            }
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.println("server stopped!");
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
        copter.clientConnectionLost();
    }

    @Override
    public void setCopter(Copter copter) {
        this.copter = copter;
    }

    @Override
    public Copter getCopter() {
        return copter;
    }

    void setOnServerStart(ServerCallback onServerStart) {
        this.onServerStart = onServerStart;
    }

    void setOnChannelRead(ServerCallback onChannelRead) {
        this.onChannelRead = onChannelRead;
    }


    private class Handler extends SimpleChannelInboundHandler<byte[]> {

        private final List<Byte> bytes = new ArrayList<>();

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
            for (byte b : msg) {
                bytes.add(b);
            }
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            byte[] bytesArr = new byte[bytes.size()];
            for (int i = 0; i < bytes.size(); i++) {
                bytesArr[i] = bytes.get(i);
            }
            bytes.clear();
            CopterDirection.Direction proto = CopterDirection.Direction.parseFrom(bytesArr);
            handleMessage(proto);
            ServerCallback channelRead = NetworkController.this.onChannelRead;
            if (channelRead != null) {
                channelRead.handle();
            }
        }

        private void handleMessage(CopterDirection.Direction proto) {
            if (proto.getCorrect() != CopterDirection.Direction.Correct.NONE) {
                copter.correct(proto.getCorrect());
                return;
            }
            try {
                copter.handleDirectionChange(proto);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            copter.clientConnectionLost();
            cause.printStackTrace();
            ctx.close();
        }
    }

    public interface ServerCallback {

        void handle();

    }
}
