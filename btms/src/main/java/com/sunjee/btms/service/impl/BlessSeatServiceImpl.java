package com.sunjee.btms.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.sunjee.util.HqlNoEquals;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sunjee.btms.bean.BSRecord;
import com.sunjee.btms.bean.BlessSeat;
import com.sunjee.btms.bean.Deader;
import com.sunjee.btms.bean.Enterprise;
import com.sunjee.btms.bean.Level;
import com.sunjee.btms.bean.Member;
import com.sunjee.btms.bean.Shelf;
import com.sunjee.btms.common.DataGrid;
import com.sunjee.btms.common.DonationType;
import com.sunjee.btms.common.Pager;
import com.sunjee.btms.common.SortType;
import com.sunjee.btms.dao.BSRecordDao;
import com.sunjee.btms.dao.BlessSeatDao;
import com.sunjee.btms.dao.DeaderDao;
import com.sunjee.btms.dao.LevelDao;
import com.sunjee.btms.exception.AppRuntimeException;
import com.sunjee.btms.service.BSRecordService;
import com.sunjee.btms.service.BlessSeatService;
import com.sunjee.btms.service.ShelfService;

@Service("blessSeatService")
public class BlessSeatServiceImpl implements BlessSeatService {

    private ShelfService shelfService;
    private BlessSeatDao blessSeatDao;
    private DeaderDao deaderDao;
    private BSRecordDao bsRecordDao;
    private BSRecordService bsRecordService;
    private LevelDao levelDao;


    public ShelfService getShelfService() {
        return shelfService;
    }

    @Resource(name = "shelfService")
    public void setShelfService(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    public BlessSeatDao getBlessSeatDao() {
        return blessSeatDao;
    }

    @Resource(name = "blessSeatDao")
    public void setBlessSeatDao(BlessSeatDao blessSeatDao) {
        this.blessSeatDao = blessSeatDao;
    }

    public DeaderDao getDeaderDao() {
        return deaderDao;
    }

    @Resource(name = "deaderDao")
    public void setDeaderDao(DeaderDao deaderDao) {
        this.deaderDao = deaderDao;
    }

    public BSRecordDao getBsRecordDao() {
        return bsRecordDao;
    }

    @Resource(name = "bsRecordDao")
    public void setBsRecordDao(BSRecordDao bsRecordDao) {
        this.bsRecordDao = bsRecordDao;
    }

    public BSRecordService getBsRecordService() {
        return bsRecordService;
    }

    @Resource(name = "bsRecordService")
    public void setBsRecordService(BSRecordService bsRecordService) {
        this.bsRecordService = bsRecordService;
    }

    public LevelDao getLevelDao() {
        return levelDao;
    }

    @Resource(name = "levelDao")
    public void setLevelDao(LevelDao levelDao) {
        this.levelDao = levelDao;
    }

    @Override
    public void addBlessSeat(BlessSeat bs) {
        if (this.getBlessSeatByBSCode(bs.getBsCode()) != null) {
            throw new AppRuntimeException("福位编号不能重复");
        }
        this.blessSeatDao.saveEntity(bs);
    }

    @Override
    public DataGrid<BlessSeat> getDataGrid(Pager page,
                                           Map<String, Object> whereParams, Map<String, SortType> sortParams) {
        return this.blessSeatDao.getDataGrid(page, whereParams, sortParams);
    }

    @Override
    public int updateBlessSeatLeve(String bsIds[], Level level) {
        level = this.levelDao.getEntityById(level.getLevId());
        Map<String, Object> values = new HashMap<>();
        values.put("lev", level);
        values.put("managExpense", level.getMngPrice());

        Map<String, Object> params = new HashMap<>();
        for (String id : bsIds) {
            params.put("bsId", id);
            this.blessSeatDao.updateEntity(values, params);
        }
        return 0;
    }

    @Override
    public BlessSeat add(BlessSeat bs) {
        if (this.getBlessSeatByBSCode(bs.getBsCode()) != null) {
            throw new AppRuntimeException("福位编号不能重复");
        }
        return this.blessSeatDao.saveEntity(bs);
    }

    @Override
    public void update(BlessSeat bs) {
        this.blessSeatDao.updateEntity(bs);
    }

    @Override
    public List<BlessSeat> getAllByParams(Pager page,
                                          Map<String, Object> whereParams, Map<String, SortType> sortParams) {
        return this.blessSeatDao.getEntitys(page, whereParams, sortParams);
    }

    @Override
    public BlessSeat getById(String id) {
        return this.blessSeatDao.getEntityById(id);
    }

    @Override
    public void delete(BlessSeat bs) {
        this.blessSeatDao.deletEntity(bs);
    }

    @Override
    public void updatePermitByShelfId(String shelfId, boolean permit) {
        if (shelfId == null) {
            return;
        }
        Map<String, Object> whereParams = new HashMap<>();
        whereParams.put("shelf.shelfId", shelfId);
        whereParams.put("permit", !permit);
        List<BlessSeat> bsLs = this.blessSeatDao.getEntitys(null, whereParams, null);
        for (BlessSeat bs : bsLs) {
            if (permit) {
                this.updateEnable(bs.getBsId());
            } else {
                this.updateDisable(bs.getBsId(), true);    //禁用福位，并更新与福位关联的使用者和捐赠记录
            }
        }
    }

    @Override
    public DataGrid<BlessSeat> getEnableDataGrid(Pager pager,
                                                 Map<String, Object> whereParams, Map<String, SortType> sortParams) {
        return this.blessSeatDao.getEnableDataGrid(pager, whereParams,
                sortParams);
    }

    @Override
    public DataGrid<BlessSeat> getSaledGrid(Member member, Pager pager,
                                            String searchKey, Map<String, SortType> sortParams) {
        return this.blessSeatDao.getSaledGrid(member, pager, searchKey,
                sortParams);
    }

    @Override
    public DataGrid<BlessSeat> getSaledGrid(Enterprise enterprise, Pager pager,
                                            String searchKey, Map<String, SortType> sortParams) {
        return this.blessSeatDao.getSaledGrid(enterprise, pager, searchKey,
                sortParams);
    }

    @Override
    public DataGrid<BlessSeat> getDataGridOnMember(Member member, Pager pager,
                                                   Map<String, Object> whereParams, Map<String, SortType> sortParams) {
        return this.blessSeatDao.getDataGridOnMember(member, pager, whereParams, sortParams);
    }

    @Override
    public DataGrid<BlessSeat> getEnableUseBlessSeatGrid(Pager pager,
                                                         Map<String, Object> whereParams, Map<String, SortType> sortParams) {
        return this.blessSeatDao.getEnableUseBlessSeatGrid(pager, whereParams, sortParams);
    }

    @Override
    public int getRemainCount() {
        String values[] = new String[]{"count(bsId)"};
        Map<String, Object> param = new HashMap<>();
        param.put("permit", true);
        List<Object[]> ls = this.blessSeatDao.getParams(values, null, param, null);
        Object result = ls.get(0);
        long count = (long) result;
        int buyed = this.bsRecordService.getPermitCount();
        int remain = Integer.parseInt(String.valueOf(count - buyed));
        return remain;
    }

    @Override
    public int updatePermit(String[] bsIds, boolean b) {
        int count = 0;
        if (bsIds == null) {
            return count;
        }
        for (String bsId : bsIds) {
            if (b) {
                updateEnable(bsId);
            } else {
                updateDisable(bsId, true);
            }
        }
        return count;
    }

    @Override
    public int updateDisable(String bsId, boolean flag) {
        Map<String, Object> whereParams = new HashMap<>();
        Map<String, Object> values = new HashMap<>();
        if (flag) {
            BlessSeat bs = this.blessSeatDao.getEntityById(bsId);
            if (bs != null) {
                Deader dead = bs.getDeader();
                if (dead != null) {
                    this.deaderDao.deletEntity(dead);    //删除福位对应的使用者
                }
                whereParams.clear();
                whereParams.put("blessSeat.bsId", bsId);
                List<BSRecord> bsList = this.bsRecordDao.getEntitys(null, whereParams, null);
                for (BSRecord bsr : bsList) {
                    if (!bsr.isPayed()) {
                        this.bsRecordDao.deletEntity(bsr);
                    } else if (bsr.getDonatType().equals(DonationType.buy) && bsr.isPermit()) {
                        bsr.setPermit(false);
                        this.bsRecordDao.updateEntity(bsr);
                    } else if (bsr.getDonatType().equals(DonationType.lease) && bsr.getDonatOverdue().after(new Date())) {
                        bsr.setDonatOverdue(new Date());
                        this.bsRecordDao.updateEntity(bsr);
                    }
                }
            }
        }
        whereParams.clear();
        whereParams.put("bsId", bsId);
        values.put("permit", false);
        return this.blessSeatDao.executeUpate(values, whereParams);
    }

    @Override
    public int updateEnable(String bsId) {
        BlessSeat bs = this.getById(bsId);
        if (bs != null && !bs.isPermit() && bs.getShelf().isPermit()) {
            bs.setPermit(true);
            this.blessSeatDao.updateEntity(bs);
            return 1;
        }
        return 0;
    }

    @Override
    public BlessSeat getBlessSeatByBSCode(String bsCode) {
        Map<String, Object> whereParams = new HashMap<>();
        whereParams.put("bsCode", bsCode);
        List<BlessSeat> ls = this.blessSeatDao.getEntitys(null, whereParams, null);
        if (ls == null || ls.size() < 1) {
            return null;
        }
        return ls.get(0);
    }

    @Override
    public void updateBlessSeatCode(String bsId, String bsCode) {
        if (StringUtils.isEmpty(bsId)) {
            throw new AppRuntimeException("自定义福位编号出错，请联系管理员");
        }
        if (StringUtils.isEmpty(bsCode)) {
            throw new AppRuntimeException("福位编号不能为空");
        }
        Map<String, Object> whereParams = new HashMap<>();
        whereParams.put("bsId", new HqlNoEquals(bsId));
        whereParams.put("bsCode", bsCode);
        List<BlessSeat> bsList = this.blessSeatDao.getEntitys(null, whereParams, null);
        if (bsList.size() > 0) {
            throw new AppRuntimeException("福位编号不能重复！");
        }
        whereParams.clear();
        whereParams.put("bsId", bsId);
        Map<String, Object> values = new HashMap<>();
        values.put("bsCode", bsCode);
        this.blessSeatDao.updateEntity(values, whereParams);
    }

    @Override
    public BlessSeat addByShelf(Shelf shelf, int shelfRow, int shelfColumn) {
        shelf = this.shelfService.getById(shelf.getShelfId());
        if (shelfRow > shelf.getShelfRow()) {
            this.shelfService.addRow(shelf, shelfRow, false);
        }
        if (shelfColumn > shelf.getShelfColumn()) {
            this.shelfService.addColumn(shelf, shelfColumn, false);
        }

        BlessSeat bs = this.getBlessSeatByBSCode(BlessSeat.getBsCodeByShelf(shelf, shelfRow, shelfColumn));
        if (bs != null && !bs.isPermit() && bs.getShelf().isPermit()) {
            bs.setPermit(true);
            this.blessSeatDao.updateEntity(bs);
        }
        return bs;
    }
}
