package emu.grasscutter.server.packet.send;

import com.google.protobuf.ByteString;

import emu.grasscutter.net.packet.BasePacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.GetPlayerTokenRspOuterClass.GetPlayerTokenRsp;
import emu.grasscutter.server.game.GameSession;
import emu.grasscutter.utils.Crypto;
import emu.grasscutter.utils.Utils;

public class PacketGetPlayerTokenRsp extends BasePacket {

	public PacketGetPlayerTokenRsp(GameSession session, int BNLDENCDKIA, String CAHLCMMFHIH) {
		super(PacketOpcodes.GetPlayerTokenRsp, true);
		
		this.setUseDispatchKey(true);
		
		GetPlayerTokenRsp p = GetPlayerTokenRsp.newBuilder()
				.setUid(session.getPlayer().getUid())
				.setToken(session.getAccount().getToken())
				.setAccountType(1)
				.setIsProficientPlayer(session.getPlayer().getAvatars().getAvatarCount() > 0)
				.setSecretKeySeed(Crypto.ENCRYPT_SEED)
				.setSecurityCmdBuffer(ByteString.copyFrom(Crypto.ENCRYPT_SEED_BUFFER))
				.setPlatformType(3)
				.setChannelId(1)
				.setCountryCode("US")
				.setClientVersionRandomKey("c25-314dd05b0b5f")
				.setRegPlatform(3)
				.setClientIpStr(session.getAddress().getAddress().getHostAddress())
				.setPAKPELLDKEM("ZnVjaw==")
				.setBNLDENCDKIA(BNLDENCDKIA)
				.setCAHLCMMFHIH(CAHLCMMFHIH)
				.build();
	
		this.setData(p.toByteArray());
	}
}
