/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package handlers.bypasshandlers;

import org.l2j.gameserver.data.xml.impl.MultisellData;
import org.l2j.gameserver.handler.IBypassHandler;
import org.l2j.gameserver.model.actor.Creature;
import org.l2j.gameserver.model.actor.Npc;
import org.l2j.gameserver.model.actor.instance.Player;

import static org.l2j.gameserver.util.GameUtils.isNpc;

public class Multisell implements IBypassHandler
{
	private static final String[] COMMANDS =
	{
		"multisell",
		"exc_multisell"
	};
	
	@Override
	public boolean useBypass(String command, Player player, Creature target)
	{
		if (!isNpc(target))
		{
			return false;
		}
		
		try
		{
			int listId;
			if (command.toLowerCase().startsWith(COMMANDS[0])) // multisell
			{
				listId = Integer.parseInt(command.substring(9).trim());
				MultisellData.getInstance().separateAndSend(listId, player, (Npc) target, false);
				return true;
			}
			else if (command.toLowerCase().startsWith(COMMANDS[1])) // exc_multisell
			{
				listId = Integer.parseInt(command.substring(13).trim());
				MultisellData.getInstance().separateAndSend(listId, player, (Npc) target, true);
				return true;
			}
			return false;
		}
		catch (Exception e)
		{
			LOGGER.warn("Exception in " + getClass().getSimpleName(), e);
		}
		return false;
	}
	
	@Override
	public String[] getBypassList()
	{
		return COMMANDS;
	}
}
